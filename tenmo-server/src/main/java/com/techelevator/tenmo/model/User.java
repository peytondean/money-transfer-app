package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.*;

/*
 * User JPA Model
 *
 * */



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "username", "activated"})
@ToString(exclude = {"password", "accounts"})
@Entity
@Table(name = "tenmo_user")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "user_id")
   private Integer id;

   @Column(length = 50, unique = true, nullable = false)
   private String username;

   @JsonIgnore // prevent from being sent to client, don't want to just give it all away.
   @Transient
   private String password;

   @JsonIgnore
   @Transient
   private boolean activated;

   @Transient
   private Set<Authority> authorities = new HashSet<>();

   @Column(name = "password_hash", length = 200, nullable = false)
   private String passwordHash;

   @JsonIgnore
   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<Account> accounts = new HashSet<>();

   public User(int id, String username, String password, String authorities) {
      this.id = id;
      this.username = username;
      this.password = password;
      if (authorities != null) this.setAuthorities(authorities);
      this.activated = true;
   }

   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for (String role : roles) {
         this.authorities.add(new Authority("ROLE_" + role));
      }
   }



}
