package at.team2.database_wrapper.common;

import at.team2.database_wrapper.enums.ConnectorType;
import at.team2.domain.interfaces.DomainEntityProperty;

import java.security.InvalidParameterException;

public class FilterConnector<L extends DomainEntityProperty, R extends DomainEntityProperty> {
    private ConnectorType _connectorType;
    private Filter<L> _leftFilter;
    private FilterConnector<L, R> _leftFilterConnector;
    private Filter<R> _rightFilter;
    private FilterConnector<L, R> _rightFilterConnector;

    public FilterConnector(Filter<L> leftFilter) {
        if(leftFilter == null) {
            throw new InvalidParameterException("leftFilterItem cannot be null");
        }

        _leftFilter = leftFilter;
    }

    public FilterConnector(FilterConnector<L, R> leftFilterConnector, ConnectorType connectorType, FilterConnector<L, R> rightFilterConnector) {
        if(leftFilterConnector == null) {
            throw new InvalidParameterException("leftFilterConnector cannot be null");
        }

        if(connectorType == null) {
            throw new InvalidParameterException("connectorType cannot be null");
        }

        if(rightFilterConnector == null) {
            throw new InvalidParameterException("rightFilterConnector cannot be null");
        }

        _leftFilterConnector = leftFilterConnector;
        _connectorType = connectorType;
        _rightFilterConnector = rightFilterConnector;
    }

    public FilterConnector(Filter<L> leftFilter, ConnectorType connectorType, FilterConnector<L, R> rightFilterConnector) {
        if(leftFilter == null) {
            throw new InvalidParameterException("leftFilter cannot be null");
        }

        if(connectorType == null) {
            throw new InvalidParameterException("connectorType cannot be null");
        }

        if(rightFilterConnector == null) {
            throw new InvalidParameterException("rightFilterConnector cannot be null");
        }
    }

    public FilterConnector(FilterConnector<L, R> leftFilterConnector, ConnectorType connectorType, Filter<R> rightFilter) {
        if(leftFilterConnector == null) {
            throw new InvalidParameterException("leftFilterConnector cannot be null");
        }

        if(connectorType == null) {
            throw new InvalidParameterException("connectorType cannot be null");
        }

        if(rightFilter == null) {
            throw new InvalidParameterException("rightFilterConnector cannot be null");
        }

        _leftFilterConnector = leftFilterConnector;
        _connectorType = connectorType;
        _rightFilter = rightFilter;
    }

    public FilterConnector(Filter<L> leftFilter, ConnectorType connectorType, Filter<R> rightFilter) {
        this(leftFilter);

        if(connectorType == null) {
            throw new InvalidParameterException("connectorType cannot be null");
        }

        if(rightFilter == null) {
            throw new InvalidParameterException("rightFilter cannot be null");
        }

        _connectorType = connectorType;
        _rightFilter = rightFilter;
    }

    public ConnectorType getConnectorType() {
        return _connectorType;
    }

    public Filter<L> getLeftFilter() {
        return _leftFilter;
    }

    public FilterConnector<L, R> getLeftFilterConnector() {
        return _leftFilterConnector;
    }

    public Filter<R> getRightFilter() {
        return _rightFilter;
    }

    public FilterConnector<L, R> getRightFilterConnector() {
        return _rightFilterConnector;
    }
}