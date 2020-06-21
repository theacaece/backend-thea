package edu.caece.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(FileReferenceId.class)
public class FileReference {

  @Id
  @Column
  private String node;

  @Id
  @Column(length = 1024)
  private String path;

}
