/*
 * SBML Graph
 * Simple API to trigger KGML to SBML Conversions and GraphDB-Persisting [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/). 
 *
 * OpenAPI spec version: 1.0.1
 * Contact: tiede@informatik.uni-tuebingen.de
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package rest.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

/**
 * NodeEdgeListEntry
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2018-09-21T12:10:20.804Z[GMT]")public class NodeEdgeListEntry   {
  @JsonProperty("node1")
  private String node1 = null;

  @JsonProperty("node2")
  private String node2 = null;

  @JsonProperty("edge")
  private String edge = null;

  public NodeEdgeListEntry node1(String node1) {
    this.node1 = node1;
    return this;
  }

  /**
   * Get node1
   * @return node1
   **/
  @JsonProperty("node1")
  @Schema(description = "")
  public String getNode1() {
    return node1;
  }

  public void setNode1(String node1) {
    this.node1 = node1;
  }

  public NodeEdgeListEntry node2(String node2) {
    this.node2 = node2;
    return this;
  }

  /**
   * Get node2
   * @return node2
   **/
  @JsonProperty("node2")
  @Schema(description = "")
  public String getNode2() {
    return node2;
  }

  public void setNode2(String node2) {
    this.node2 = node2;
  }

  public NodeEdgeListEntry edge(String edge) {
    this.edge = edge;
    return this;
  }

  /**
   * Get edge
   * @return edge
   **/
  @JsonProperty("edge")
  @Schema(description = "")
  public String getEdge() {
    return edge;
  }

  public void setEdge(String edge) {
    this.edge = edge;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NodeEdgeListEntry nodeEdgeListEntry = (NodeEdgeListEntry) o;
    return Objects.equals(this.node1, nodeEdgeListEntry.node1) &&
        Objects.equals(this.node2, nodeEdgeListEntry.node2) &&
        Objects.equals(this.edge, nodeEdgeListEntry.edge);
  }

  @Override
  public int hashCode() {
    return Objects.hash(node1, node2, edge);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NodeEdgeListEntry {\n");
    
    sb.append("    node1: ").append(toIndentedString(node1)).append("\n");
    sb.append("    node2: ").append(toIndentedString(node2)).append("\n");
    sb.append("    edge: ").append(toIndentedString(edge)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
