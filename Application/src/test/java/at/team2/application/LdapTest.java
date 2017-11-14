package at.team2.application;

import org.junit.Test;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;

import at.team2.application.helper.LdapHelper;

public class LdapTest {
    @Test
    public void ldapConnection() throws NamingException {
        // https://inside.fhv.at/display/is/LDAP+Verzeichnis
        // a VPN connection to the FHV network is required

        /*String searchBase = "o=fhv.at";
        String username = "hrh5775";
        String additionalDNInformation = "ou=people,o=fhv.at";
        String password = "myOwnPassword";*/

        String searchBase = "o=fhv.at";
        String username = "tf-test";
        String additionalDNInformation = "uid=tf-test,ou=special,o=fhv.at";//"dc=uclv,dc=net";
        String password = null;

        String ldapAdServer = "ldap://openldap.fhv.at";
        String fullDNInformation = "uid=" + username + (additionalDNInformation != null && !additionalDNInformation.isEmpty() ?
                "," + additionalDNInformation : "");

        Context context = LdapHelper.getContext(ldapAdServer, true, fullDNInformation, password);
        SearchResult result = LdapHelper.findUser((LdapContext) context, searchBase, "hrh5775");

        if(result != null) {
            System.out.println("user available:");

            Attributes attributes = result.getAttributes();
            System.out.println("+ " + attributes.get("displayname").get(0));
            System.out.println("+ " + attributes.get("givenname").get(0));
            System.out.println("+ " + attributes.get("userpassword"));
            System.out.println("+ " + attributes.get("maillocaladdress").get(0));
            System.out.println("+ " + attributes.get("mail").get(0));
            System.out.println("+ " + attributes.get("uidnumber").get(0));
            System.out.println("+ " + attributes.get("cn").get(0));
            System.out.println("+ " + attributes.get("fhvpersoncardid").get(0));
            System.out.println("+ " + attributes.get("uid").get(0));
            System.out.println("+ " + attributes.get("o").get(0));
            System.out.println("+ " + attributes.get("homedirectory").get(0));
        } else {
            System.out.println("user not available");
        }

        System.out.println();

        if(LdapHelper.hasValidCredentials(ldapAdServer, true, fullDNInformation, password)) {
            System.out.println("Credentials are correct");
        } else {
            System.out.println("Credentials are incorrect");
        }

        context.close();

        //String searchBase = "dc=example,dc=com";
        //Context context = LdapHelper.getContext("ldap://ldap.forumsys.com:389", searchBase, "uid=tesla,dc=example,dc=com", "password");
        //LdapHelper.findUser(context, searchBase, "riemann");
    }
}