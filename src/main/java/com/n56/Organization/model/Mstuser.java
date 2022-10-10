/*
 * package com.n56.Organization.model; import javax.persistence.Column; import
 * javax.persistence.Entity; import javax.persistence.GeneratedValue; import
 * javax.persistence.GenerationType; import javax.persistence.Id; import
 * javax.persistence.JoinColumn; import javax.persistence.ManyToOne; import
 * javax.persistence.SequenceGenerator; import javax.persistence.Table;
 * 
 * import lombok.AllArgsConstructor; import lombok.Getter; import
 * lombok.NoArgsConstructor; import lombok.Setter; import lombok.ToString;
 * 
 * @Entity
 * 
 * @Table (name = "mst_user")
 * 
 * @Getter
 * 
 * @Setter
 * 
 * @NoArgsConstructor
 * 
 * @AllArgsConstructor
 * 
 * @ToString public class Mstuser {
 * 
 * @Id
 * 
 * @Column(name = "user_id")
 * 
 * @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq",
 * allocationSize = 1)
 * 
 * @GeneratedValue(strategy = GenerationType.SEQUENCE, generator =
 * "user_id_seq") private Integer mst_userId;
 * 
 * @Column(name = "email") private String email;
 * 
 * @Column(name = "password") private String password;
 * 
 * @Column(name = "active") private String active;
 * 
 * @Column (name = "file_data") private String filedata;
 * 
 * @Column(name = "file_path") private String filepath;
 * 
 * @Column(name = "file_type") private String filetype; }
 */