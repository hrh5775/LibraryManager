package team2.database_wrapper.entities;

import team2.database_wrapper.helper.TypeHelper;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Account", schema = "Library", catalog = "")
public class AccountEntity {
    private int id;
    private int accountRoleId;
    private String userName;
    private String password;
    private byte active;
    private AccountRoleEntity accountRoleByAccountRoleId;
    private Collection<CustomerEntity> customersById;
    private Collection<StaffEntity> staffById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "accountRoleId", nullable = false)
    public int getAccountRoleId() {
        return accountRoleId;
    }

    public void setAccountRoleId(int accountRoleId) {
        this.accountRoleId = accountRoleId;
    }

    @Basic
    @Column(name = "userName", nullable = false, length = 45)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "active", nullable = false)
    public boolean getActive() {
        return TypeHelper.toBoolean(active);
    }

    public void setActive(boolean active) {
        this.active = TypeHelper.toByte(active);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountEntity that = (AccountEntity) o;

        if (id != that.id) return false;
        if (accountRoleId != that.accountRoleId) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (active != that.active) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + accountRoleId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (int) active;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "accountRoleId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public AccountRoleEntity getAccountRoleByAccountRoleId() {
        return accountRoleByAccountRoleId;
    }

    public void setAccountRoleByAccountRoleId(AccountRoleEntity accountRoleByAccountRoleId) {
        this.accountRoleByAccountRoleId = accountRoleByAccountRoleId;
    }

    @OneToMany(mappedBy = "accountByAccountId")
    public Collection<CustomerEntity> getCustomersById() {
        return customersById;
    }

    public void setCustomersById(Collection<CustomerEntity> customersById) {
        this.customersById = customersById;
    }

    @OneToMany(mappedBy = "accountByAccountId")
    public Collection<StaffEntity> getStaffById() {
        return staffById;
    }

    public void setStaffById(Collection<StaffEntity> staffById) {
        this.staffById = staffById;
    }
}
