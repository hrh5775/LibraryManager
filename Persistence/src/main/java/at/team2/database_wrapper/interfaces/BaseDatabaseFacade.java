package at.team2.database_wrapper.interfaces;

import at.team2.database_wrapper.common.FilterItem;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.SessionFactory;
import at.team2.domain.interfaces.BaseDomainEntity;
import at.team2.domain.interfaces.DomainEntityProperty;
import javafx.util.Pair;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseDatabaseFacade<V extends BaseDomainEntity, P extends DomainEntityProperty> implements Session, Editable<V>, Filterable<V, P> {
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

    protected Query getByFilter(String queryString, EntityManager session, List<FilterItem<P>> filterItems) {
        StringBuilder builder = new StringBuilder();
        String columnIdentifier;
        String modifiedColumnIdentifier = null;
        String parameter;
        String match = null;
        String parameterStart = null;
        String parameterEnd = null;
        FilterItem filterItem;
        List<Pair<String, Object>> parameterList = new LinkedList<>();

        builder.append(queryString + " ");

        for(int i = 0; i < filterItems.size(); i++) {
            filterItem = filterItems.get(i);
            columnIdentifier = getColumnNameForProperty((P) filterItem.getProperty());

            if(columnIdentifier != null || !columnIdentifier.trim().isEmpty()) {
                if(!(filterItem.getParameter() instanceof Integer)) {
                    parameter = filterItem.getParameter().toString();

                    switch (filterItems.get(i).getCaseType()) {
                        case NORMAL:
                            break;
                        case IGNORE_CASE:
                            parameter = parameter.toUpperCase();
                            modifiedColumnIdentifier = "upper(" + columnIdentifier + ")";
                            break;
                    }

                    parameterList.add(new Pair<>(columnIdentifier, parameter));
                } else {
                    parameterList.add(new Pair<>(columnIdentifier, filterItem.getProperty()));
                }

                switch (filterItems.get(i).getMatchType()) {
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
                        parameterStart = "";
                        parameterEnd = "";
                        match = "in";
                        break;
                    case STARTS:
                        parameterStart = "";
                        parameterEnd = "%";
                        match = "=";
                        break;
                    case ENDS:
                        parameterStart = "%";
                        parameterEnd = "";
                        match = "=";
                        break;
                }

                builder.append(modifiedColumnIdentifier + " " + match + " " + parameterStart + " :" + columnIdentifier + " " + parameterEnd);

                if(i < filterItems.size() -1) {
                    builder.append(" and ");
                }
            }
        }

        Query query = session.createQuery(builder.toString());

        for(Pair<String, Object> item : parameterList) {
            query.setParameter(item.getKey(), item.getValue());
        }

        return query;
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