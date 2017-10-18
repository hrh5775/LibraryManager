package team2.server.helper;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

public class LdapHelper {
    /*final String ldapAdServer = "ldap://ad.your-server.com:389";
    final String ldapSearchBase = "dc=ad,dc=my-domain,dc=com";

    final String username = "tf-test";
    final String password = "tf-test";*/

    public static LdapContext getContext(String ldapAdServer, String ldapSearchBase, String username, String password) throws NamingException {
        Hashtable<String, Object> env = new Hashtable();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");

        env.put(Context.SECURITY_PRINCIPAL, username);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapAdServer);
        env.put("com.sun.jndi.ldap.read.timeout", "5000");
        env.put("com.sun.jndi.ldap.connect.timeout", "5000");

        //ensures that objectSID attribute values
        //will be returned as a byte[] instead of a String
        env.put("java.naming.ldap.attributes.binary", "objectSID");

        // the following is helpful in debugging errors
        env.put("com.sun.jndi.ldap.trace.ber", System.err);

        LdapContext context = new InitialLdapContext(env, null);
        return context;
    }

    public static void findUser(LdapContext context, String ldapSearchBase, String username) throws NamingException {
        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + username + "))";
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration<SearchResult> results = context.search(ldapSearchBase, searchFilter, searchControls);

        System.out.println("Connected to server");
    }

    public static void checkCredentials(String username, String password) {

    }

    public static void main(String [] args) {
        try {
//            String searchBase = "uid=tf-test,ou=special,o=fhv.at";
//            LdapContext context = LdapHelper.getContext("ldap://fhv.at:389", searchBase, "tf-test", "");
//            LdapHelper.findUser(context, searchBase, "tf-test");

            String searchBase = "dc=example,dc=com";
            LdapContext context = LdapHelper.getContext("ldap://ldap.forumsys.com:389", searchBase, "uid=tesla,dc=example,dc=com", "password");
            LdapHelper.findUser(context, searchBase, "riemann");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
