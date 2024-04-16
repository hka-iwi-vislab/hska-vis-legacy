package com.hka.verteiltesysteme.database;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;

/**
 * This class contains the users of the webshop.
 */
@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements java.io.Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	
	@Column(name = "username", unique = true, nullable = false)
	private String username;

	
	@Column(name = "name", nullable = false)
	private String firstname;

	
	@Column(name = "lastname", nullable = false)
	private String lastname;

	
	@Column(name = "password", nullable = false)
	private String password;

	@ManyToOne()
	@JoinColumn(name = "role", nullable = false)
	private Role role;
}
