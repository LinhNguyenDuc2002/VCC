package com.example.otpauthentication.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;

@Embeddable
public class EmployeeRolePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "tbl_employeeid")
    private long tblEmployeeid;

    @Basic(optional = false)
    @NotNull
    @Column(name = "tbl_roleid")
    private long tblRoleid;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) tblEmployeeid;
        hash += (int) tblRoleid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeRolePK)) {
            return false;
        }
        EmployeeRolePK other = (EmployeeRolePK) object;
        if (this.tblEmployeeid != other.tblEmployeeid) {
            return false;
        }
        if (this.tblRoleid != other.tblRoleid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.test.entity.EmployeeRolePK[ tblEmployeeid=" + tblEmployeeid + ", tblRoleid=" + tblRoleid + " ]";
    }

}

