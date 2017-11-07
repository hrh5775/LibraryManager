package at.team2.application.facade;

import at.team2.common.dto.detailed.LoanDetailedDto;
import at.team2.common.dto.small.CustomerSmallDto;
import at.team2.common.dto.small.MediaSmallDto;
import at.team2.database_wrapper.facade.*;
import at.team2.domain.entities.*;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import at.team2.application.helper.MapperHelper;
import at.team2.application.interfaces.BaseApplicationFacade;
import at.team2.database_wrapper.enums.TransactionType;
import at.team2.domain.enums.properties.LoanProperty;
import javafx.util.Pair;

public class LoanApplicationFacade extends BaseApplicationFacade<Loan, LoanDetailedDto, LoanProperty> {
    private static LoanApplicationFacade _instance;
    private LoanFacade _facade = new LoanFacade();
    private MediaMemberFacade _mediaMemberFacade = new MediaMemberFacade();
    private CustomerFacade _customerFacade = new CustomerFacade();
    private MediaFacade _mediaFacade = new MediaFacade();

    private LoanApplicationFacade() {
    }

    public static LoanApplicationFacade getInstance() {
        if(_instance == null) {
            _instance = new LoanApplicationFacade();
        }

        return _instance;
    }

    @Override
    public Loan getById(int id) {
        return _facade.getById(id);
    }

    @Override
    public List<Loan> getList() {
        return _facade.getList();
    }

    @Override
    public void closeSession() {
        _facade.closeSession();
    }

    @Override
    public Pair<Integer, List<Pair<LoanProperty, String>>> add(LoanDetailedDto value) {
        ModelMapper mapper = MapperHelper.getMapper();
        Loan entity = mapper.map(value, Loan.class);
        List<Pair<LoanProperty, String>> list = entity.validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.add(entity, TransactionType.AUTO_COMMIT),list);
        }

        return new Pair<>(0, new LinkedList<>());
    }

    @Override
    public Pair<Integer, List<Pair<LoanProperty, String>>> update(LoanDetailedDto value) {
        ModelMapper mapper = MapperHelper.getMapper();
        Loan entity = mapper.map(value, Loan.class);
        List<Pair<LoanProperty,String>> list = entity.validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.update(entity, TransactionType.AUTO_COMMIT),list);
        }

        return new Pair<>(0, new LinkedList<>());
    }

    @Override
    public Pair<Boolean, List<Pair<LoanProperty, String>>> delete(int id) {
        List<Pair<LoanProperty,String>> list = _facade.getById(id).validate();

        if(list.size() == 0) {
            return new Pair<>(_facade.delete(id, TransactionType.AUTO_COMMIT), list);
        }

        return new Pair<>(false, new LinkedList<>());
    }

    public int loanMedia(MediaSmallDto media, CustomerSmallDto customer) {
        // get a list of available media members => books, dvds, for the specified media
        MediaMember mediaMemberEntity = _mediaMemberFacade.getNotLoanedMediaMember(media.getMediaId());
        Customer customerEntity = _customerFacade.getById(customer.getId());

        if(mediaMemberEntity != null && customerEntity != null) {
            Media mediaEntity = _mediaFacade.getById(mediaMemberEntity.getMediaId());
            int loanTerm = mediaEntity.getMediaType().getLoanCondition().getLoanTerm();

            Loan loan = new Loan();
            loan.setCustomer(customerEntity);
            loan.setMediaMember(mediaMemberEntity);
            loan.setStart(new Date(Calendar.getInstance().getTime().getTime()));
            loan.setEnd(new Date(Calendar.getInstance().getTime().getTime() + (loanTerm * 24 * 3600 * 1000)));
            return _facade.add(loan, TransactionType.AUTO_COMMIT);
        }

        return 0;
    }
}