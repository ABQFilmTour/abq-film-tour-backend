package edu.cnm.deepdive.abq_film_tour_backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.net.URI;
import java.util.Date;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * This entity represents a user submitted image associated with a Film Location.
 */
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Image {

  private static EntityLinks entityLinks;

  @PostConstruct
  private void init() {
    String ignore = entityLinks.toString();
  }

  @Autowired
  private void setEntityLinks(EntityLinks entityLinks) {
    Image.entityLinks = entityLinks;
  }

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "image_id", columnDefinition = "CHAR(16) FOR BIT DATA",
      nullable = false, updatable = false)
  private UUID id;

  @NonNull
  @JsonIgnore
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @NonNull
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "film_location_id", nullable = false, updatable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private FilmLocation filmLocation;

  private String description;
  private String url;

  @Transient
  private UUID userId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", updatable = false)
  @OnDelete(action = OnDeleteAction.NO_ACTION)
  private GoogleUser user;

  @Transient
  private String googleId;

  /**
   * Flag to verify that an image has been approved by an admin and can be displayed if security
   * is tightened. Probably unnecessary for now, but better to have if we implement later.
   */
  private boolean approved;

  /**
   * Instantiates a new Image.
   */
  public Image(){
    //empty constructor
  }

  /**
   * Gets user id.
   *
   * @return the user id
   */
  @ApiModelProperty(value = "Transient ID to reference the user in a post.")
  public UUID getUserId() {
    return userId;
  }

  /**
   * Sets user id.
   *
   * @param userId the user id
   */
  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  /**
   * Gets user.
   *
   * @return the user
   */
  @ApiModelProperty(value = "The user who submitted this location.", readOnly = true)
  public GoogleUser getUser() {
    return user;
  }

  /**
   * Sets user.
   *
   * @param user the user
   */
  public void setUser(GoogleUser user) {
    this.user = user;
  }

  /**
   * Gets entity links.
   *
   * @return the entity links
   */
  public static EntityLinks getEntityLinks() {
    return entityLinks;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  @ApiModelProperty(value = "Internal ID for this location.", readOnly = true)
  public UUID getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(UUID id) {
    this.id = id;
  }

  /**
   * Gets time of creation.
   *
   * @return the time of creation
   */
  @JsonIgnore
  @ApiModelProperty(value = "The time this entity was created.", readOnly = true)
  public Date getCreated() {
    return created;
  }

  /**
   * Sets time of creation
   *
   * @param created the time of creation
   */
  @JsonProperty
  public void setCreated(Date created) {
    this.created = created;
  }

  /**
   * Gets the film location associated with this entity.
   *
   * @return the film location associated with this entity.
   */
  public FilmLocation getFilmLocation() {
    return filmLocation;
  }

  /**
   * Sets the film location associated with this entity.
   *
   * @param filmLocation the film location associated with this entity.
   */
  public void setFilmLocation(
      FilmLocation filmLocation) {
    this.filmLocation = filmLocation;
  }

  /**
   * Gets a description of the image's contents.
   *
   * @return the description of the image's contents.
   */
  @ApiModelProperty(value = "An optional description of the image's contents.")
  public String getDescription() {
    return description;
  }

  /**
   * Sets a description of the image's contents.
   *
   * @param description the description of the image's contents.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets url.
   *
   * @return the url
   */
  @ApiModelProperty(value = "The URL the image is located at.")
  public String getUrl() {
    return url;
  }

  /**
   * Sets url.
   *
   * @param url the url
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Get href uri.
   *
   * @return the uri
   */
  public URI getHref(){return entityLinks.linkForSingleResource(Image.class, id).toUri();}

  /**
   * Checks if this content is approved.
   *
   * @return the approval status
   */
  @ApiModelProperty(value = "The approval status of the location.", required = true)
  public boolean isApproved() {
    return approved;
  }

  /**
   * Changes this content's approval status.
   *
   * @param approved a boolean.
   */
  public void setApproved(boolean approved) {
    this.approved = approved;
  }

  /**
   * Used transiently to assign Google information to user submitted content.
   */
  @ApiModelProperty(value = "Transient Google ID of the user, used to reference the user during a post.")
  public String getGoogleId() {
    return googleId;
  }

  /**
   * Used transiently to assign Google information to user submitted content.
   */
  public void setGoogleId(String googleId) {
    this.googleId = googleId;
  }
}