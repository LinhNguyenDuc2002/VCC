package com.example.ldap.dto;

import org.springframework.ldap.core.ContextMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public class LdapUserMapper implements ContextMapper<LdapUser> {
    @Override
    public LdapUser mapFromContext(Object o) throws NamingException {
        LdapUser user = new LdapUser();
        user.setSn(((Attributes) o).get("sn").get().toString());
        user.setCn(((Attributes) o).get("cn").get().toString());
//        user.setUid(((Attributes) o).get("uid").get().toString());
        return null;
    }
}
