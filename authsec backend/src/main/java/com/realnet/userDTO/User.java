package com.realnet.userDTO;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "sys_account")
@EqualsAndHashCode(callSuper = false)
public class User extends Rn_Who_Columns { // implements Comparable<User>
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private Long userId;

    private String firstName;

    private String lastName;

    private String fullName;

    private String email;

    private String username;

    private String password;

    // added = 7.12.20
    private int menu_group_id;

    private String status; // for invitation

    private String department;

    private String about;
    private String photos;

    private String role;

    // private String company;

    private int securityProviderId;

    private int defaultCustomerId;

    private String phone;

    private String address1;

    private String address2;
    private String country;

    private String postal;

    private boolean enabled;
    private boolean isBlocked;
    private String secretQuestion;
    private String secretAnswer;
    private boolean enableBetaTesting;
    private boolean enableRenewal;
    private String pancard;

    private String working;

    private String managing_work;

    private Long checknumber;

    private Provider provider;

    private Set<Role> roles;


    private Sys_Accounts sys_account;


//	public void setChecknumber(long nextLong) {
//		this.checknumber=nextLong;
//		
//	}

//	@Column(name = "Accounts_id")
//	private int account_id;


}