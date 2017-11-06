package at.team2.application.facade;

import org.modelmapper.ModelMapper;

import java.util.LinkedList;
import java.util.List;

import at.team2.application.helper.MapperHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
import at.team2.common.dto.small.LoanSmallDto;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.database_wrapper.facade.CustomerFacade;
import at.team2.database_wrapper.facade.LoanFacade;
import at.team2.database_wrapper.facade.MediaMemberFacade;
import at.team2.domain.entities.Loan;
import at.team2.domain.enums.properties.LoanProperty;
import javafx.util.Pair;

public class LoanApplicationFacade extends BaseApplicationFacade<Loan,LoanSmallDto,LoanProperty>
{
    private static LoanApplicationFacade _instance;
    private LoanFacade _facade = new LoanFacade();


    private LoanApplicationFacade()
    {

    }

    public static LoanApplicationFacade getInstance()
    {
        if(_instance == null)
        {
            _instance = new LoanApplicationFacade();
        }
        return _instance;
    }
    @Override
    public Loan getById(int id)
    {
        return _facade.getById(id);
    }

    @Override
    public List<Loan> getList()
    {
        return _facade.getList();
    }

    @Override
    public Pair<Integer, List<Pair<LoanProperty, String>>> add(LoanSmallDto value)
    {
        Loan entity = new Loan();
        CustomerFacade customerFacade = new CustomerFacade();
        MediaMemberFacade mediaMemberFacade = new MediaMemberFacade();
        entity.setCustomer(customerFacade.getById(value.get_customerid()));
        entity.setMediaMember(mediaMemberFacade.getById(value.get_mediaMemberid()));
        entity.setEnd(value.get_end());
        entity.setStart(value.get_start());
        //todo: LoanDetailedDto to use mapping?
        List<Pair<LoanProperty,String>> list = entity.validate();
        if(list.size() == 0)
        {
            return new Pair<>(_facade.add(entity, TransactionType.AUTO_COMMIT),list);
        }
        return new Pair<>(0,new LinkedList<>());
    }

    @Override
    public Pair<Integer, List<Pair<LoanProperty, String>>> update(LoanSmallDto value)
    {
        ModelMapper mapper = MapperHelper.getMapper();
        Loan entity = mapper.map(value,Loan.class);
        List<Pair<LoanProperty,String>> list = entity.validate();
        if(list.size() == 0)
        {
            return new Pair<>(_facade.update(entity, TransactionType.AUTO_COMMIT),list);
        }
        return new Pair<>(0,new LinkedList<>());
    }

    @Override
    public Pair<Boolean, List<Pair<LoanProperty, String>>> delete(int id)
    {
        List<Pair<LoanProperty,String>> list = _facade.getById(id).validate();
        if(list.size() == 0)
        {
            return new Pair<>(_facade.delete(id,TransactionType.AUTO_COMMIT),list);
        }
        return new Pair<>(false,new LinkedList<>());
    }

    @Override
    public void closeSession()
    {

    }
}
