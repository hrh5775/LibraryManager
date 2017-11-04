package at.team2.database_wrapper.interfaces;

import at.team2.database_wrapper.common.FilterConnector;
import at.team2.database_wrapper.common.Filter;
import at.team2.database_wrapper.common.HibernateParameter;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.SessionFactory;
import at.team2.domain.interfaces.BaseDomainEntity;
import at.team2.domain.interfaces.DomainEntityProperty;
import javafx.util.Pair;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseDatabaseFacade<V extends BaseDomainEntity, P extends DomainEntityProperty> implements Session, Editable<V>, Filterable<V, P, P> {
    private EntityManager _session;

    public BaseDatabaseFacade() {
        this(SessionFactory.getSession());
    }

    public BaseDatabaseFacade(EntityManager session) {
        _session = session;
    }

    public abstract V getById(int id);

    protected <T> T getFirstOrDefault(Query query) {
        query.setMaxResults(1);
        List<T> entities = query.getResultList();

        if (entities.size() > 0) {
            return entities.get(0);
        }

        return null;
    }

    public abstract List<V> getList();

    protected Query getByFilter(String queryString, EntityManager session, FilterConnector<P, P> filterConnector) {
        Pair<StringBuilder, List<HibernateParameter>> filterExpression = getFilterExpression(filterConnector);
        Query query = session.createQuery(queryString + " " + filterExpression.getKey());

        for(HibernateParameter item : filterExpression.getValue()) {
            if(!item.getPreValue().isEmpty() || !item.getPostValue().isEmpty()) {
                query.setParameter(item.getIdentifier(), item.getPreValue() + item.getValue() + item.getPostValue());
            } else {
                query.setParameter(item.getIdentifier(), item.getValue());
            }
        }

        return query;
    }

    private Pair<StringBuilder, List<HibernateParameter>> getFilterExpression(FilterConnector<P, P> filterConnector) {
        Pair<StringBuilder, List<HibernateParameter>> result = new Pair<>(new StringBuilder(), new LinkedList<>());
        Pair<StringBuilder, List<HibernateParameter>> tmp;

        if(filterConnector.getLeftFilter() != null) {
            tmp = createFilterExpression(filterConnector.getLeftFilter());
        } else {
            tmp = getFilterExpression(filterConnector.getLeftFilterConnector());
        }

        result.getKey().append(tmp.getKey());
        result.getValue().addAll(tmp.getValue());

        if(filterConnector.getConnectorType() != null) {
            switch (filterConnector.getConnectorType()) {
                case OR:
                    result.getKey().append(" or ");
                    break;
                case AND:
                    result.getKey().append(" and ");
                    break;
            }

            if(filterConnector.getRightFilter() != null) {
                tmp = createFilterExpression(filterConnector.getRightFilter());

            } else {
                tmp = getFilterExpression(filterConnector.getLeftFilterConnector());
            }

            result.getKey().append(tmp.getKey());
            result.getValue().addAll(tmp.getValue());
        }

        return result;
    }

    private Pair<StringBuilder, List<HibernateParameter>> createFilterExpression(Filter<P> filter) {
        String columnIdentifier;
        String hibernateColumnIdentifier;
        String modifiedColumnIdentifier;
        Object parameter;
        Object modifiedParameter;
        String match = null;
        List<HibernateParameter> parameterList = new LinkedList<>();
        String parameterStart = null;
        String parameterEnd = null;

        StringBuilder builder = new StringBuilder("(");

        columnIdentifier = getColumnNameForProperty(filter.getProperty());
        parameter = filter.getParameter();

        if(columnIdentifier != null || !columnIdentifier.trim().isEmpty()) {
            hibernateColumnIdentifier = columnIdentifier.replace(".", "");
            modifiedColumnIdentifier = columnIdentifier;

            if(!(parameter instanceof Integer)) {
                modifiedParameter = parameter.toString();

                switch (filter.getCaseType()) {
                    case NORMAL:
                        break;
                    case IGNORE_CASE:
                        modifiedParameter = ((String)modifiedParameter).toLowerCase();
                        modifiedColumnIdentifier = "lower(" + columnIdentifier + ")";
                        break;
                }
            } else {
                modifiedParameter = parameter;
            }

            switch (filter.getMatchType()) {
                case EQUALS:
                    parameterStart = "";
                    parameterEnd = "";
                    match = "=";
                    break;
                case LESS_THAN:
                    parameterStart = "";
                    parameterEnd = "";
                    match = "<";
                    break;
                case LESS_OR_EQUAL_THAN:
                    parameterStart = "";
                    parameterEnd = "";
                    match = "<=";
                    break;
                case GREATER_THAN:
                    parameterStart = "";
                    parameterEnd = "";
                    match = ">";
                    break;
                case GREATER_OR_EQUAL_THAN:
                    parameterStart = "";
                    parameterEnd = "";
                    match = ">=";
                    break;
                case CONTAINS:
                    parameterStart = "%";
                    parameterEnd = "%";
                    match = "like";
                    break;
                case STARTS:
                    parameterStart = "";
                    parameterEnd = "%";
                    match = "like";
                    break;
                case ENDS:
                    parameterStart = "%";
                    parameterEnd = "";
                    match = "like";
                    break;
            }

            parameterList.add(new HibernateParameter(hibernateColumnIdentifier, modifiedParameter, parameterStart, parameterEnd));
            builder.append(modifiedColumnIdentifier + " " + match + " " + " :" + hibernateColumnIdentifier);
        }

        builder.append(")");
        return new Pair<>(builder, parameterList);
    }

    protected abstract String getColumnNameForProperty(P property);

    @Override
    public void closeSession() {
        if(_session != null) {
            _session.close();
            _session = null;
        }
    }

    @Override
    public EntityManager getCurrentSession() {
        return _session;
    }

    @Override
    public EntityManager getCurrentSession(TransactionType transactionType) {
        if(transactionType == TransactionType.AUTO_COMMIT) {
            _session.getTransaction().begin();
        }

        return _session;
    }
}