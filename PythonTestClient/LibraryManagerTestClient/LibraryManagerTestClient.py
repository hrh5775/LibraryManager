
from suds.client import Client
import sys
import logging
logging.getLogger('suds.client').setLevel(logging.CRITICAL)
URL = sys.argv.pop(0)
Username = raw_input("Please enter your username: ")
Password = raw_input("Please enter the password for your account: ")
Accountwsdl = URL + '/AccountService/Account?wsdl'
Accountclient = Client(Accountwsdl)
Account = Accountclient.service.login(Username,Password)
MediaID = raw_input("Please enter the Media ID: ")
Loanwsdl = URL + '/LoanService/Loan?wsdl'
Loanclient = Client(Loanwsdl)
Mediawsdl = URL + '/MediaMemberService/MediaMember?wsdl'
Mediaclient = Client(Mediawsdl)
Book = Mediaclient.service.getMediaMemberSmallById(MediaID)
Customerwsdl = URL + '/CustomerService/Customer?wsdl'
Customerclient = Client(Customerwsdl)
Customer = Customerclient.service.getCustomerSmallById(2)
returnnumber = Loanclient.service.loanMediaMember(Book,Customer,Account)

if returnnumber > 0:
    print 'Loan successful loannumber: '
    print returnnumber
else:
    print 'Loan failed'









