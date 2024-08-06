package com.example.otpauthentication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "tbl_employee_role")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name = "EmployeeRole.findAll", query = "SELECT e FROM EmployeeRole e"),
        @NamedQuery(name = "EmployeeRole.findByNote", query = "SELECT e FROM EmployeeRole e WHERE e.note = :note"),
        @NamedQuery(name = "EmployeeRole.findByTblEmployeeid", query = "SELECT e FROM EmployeeRole e WHERE e.employeeRolePK.tblEmployeeid = :tblEmployeeid"),
        @NamedQuery(name = "EmployeeRole.findByTblRoleid", query = "SELECT e FROM EmployeeRole e WHERE e.employeeRolePK.tblRoleid = :tblRoleid")})
public class EmployeeRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected EmployeeRolePK employeeRolePK;

    @Column(name = "note")
    private String note;

    @JoinColumn(name = "tbl_employeeid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employee employee;

    @JoinColumn(name = "tbl_roleid", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Role role;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeRolePK != null ? employeeRolePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeRole)) {
            return false;
        }
        EmployeeRole other = (EmployeeRole) object;
        if ((this.employeeRolePK == null && other.employeeRolePK != null) || (this.employeeRolePK != null && !this.employeeRolePK.equals(other.employeeRolePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.test.entity.EmployeeRole[ employeeRolePK=" + employeeRolePK + " ]";
    }

}
