package at.team2.application.helper;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

public class LdapHelper {
    public static Context getContext(String ldapAdServer, boolean useSSL, String username, String password) throws NamingException {
        Hashtable<String, Object> env = new Hashtable();
        String authentication;

        if(useSSL) {
            env.put(Context.SECURITY_PROTOCOL, "ssl");
        }

        env.put(Context.SECURITY_PRINCIPAL, username);

        // http://docs.oracle.com/javase/jndi/tutorial/ldap/security/ldap.html
        if(password == null || password.isEmpty()) {
            authentication = "none";
        } else {
            authentication = "simple";
            env.put(Context.SECURITY_CREDENTIALS, password);
        }

        env.put(Context.SECURITY_AUTHENTICATION, authentication);

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, ldapAdServer);
        env.put("com.sun.jndi.ldap.read.timeout", "5000");
        env.put("com.sun.jndi.ldap.connect.timeout", "5000");

        //ensures that objectSID attribute values
        //will be returned as a byte[] instead of a String
        //env.put("java.naming.ldap.attributes.binary", "objectSID");

        // the following is helpful in debugging errors
        //env.put("com.sun.jndi.ldap.trace.ber", System.err);

        return new InitialLdapContext(env, null);
    }

    public static SearchResult findUser(LdapContext context, String ldapSearchBase, String username) throws NamingException {
        String searchFilter = "(uid=" + username + ")";
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration<SearchResult> results = context.search(ldapSearchBase, searchFilter, searchControls);

        if(results.hasMore()) {
            return results.next();
        }

        return null;
    }

    public static boolean hasValidCredentials(String ldapAdServer, boolean useSSL, String username, String password) {
        try {
            Context context = getContext(ldapAdServer, useSSL, username, password);

            if(context != null) {
                context.close();
                return true;
            }
        } catch (NamingException e) {
        }

        return false;
    }
}