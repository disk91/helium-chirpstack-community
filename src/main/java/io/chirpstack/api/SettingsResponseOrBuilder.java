// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: api/internal.proto
// Protobuf Java Version: 4.29.2

package io.chirpstack.api;

public interface SettingsResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:api.SettingsResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * OpenId Connect settings.
   * </pre>
   *
   * <code>.api.OpenIdConnect openid_connect = 1;</code>
   * @return Whether the openidConnect field is set.
   */
  boolean hasOpenidConnect();
  /**
   * <pre>
   * OpenId Connect settings.
   * </pre>
   *
   * <code>.api.OpenIdConnect openid_connect = 1;</code>
   * @return The openidConnect.
   */
  io.chirpstack.api.OpenIdConnect getOpenidConnect();
  /**
   * <pre>
   * OpenId Connect settings.
   * </pre>
   *
   * <code>.api.OpenIdConnect openid_connect = 1;</code>
   */
  io.chirpstack.api.OpenIdConnectOrBuilder getOpenidConnectOrBuilder();

  /**
   * <pre>
   * OAuth2 settings.
   * </pre>
   *
   * <code>.api.OAuth2 oauth2 = 2;</code>
   * @return Whether the oauth2 field is set.
   */
  boolean hasOauth2();
  /**
   * <pre>
   * OAuth2 settings.
   * </pre>
   *
   * <code>.api.OAuth2 oauth2 = 2;</code>
   * @return The oauth2.
   */
  io.chirpstack.api.OAuth2 getOauth2();
  /**
   * <pre>
   * OAuth2 settings.
   * </pre>
   *
   * <code>.api.OAuth2 oauth2 = 2;</code>
   */
  io.chirpstack.api.OAuth2OrBuilder getOauth2OrBuilder();

  /**
   * <pre>
   * Tileserver URL.
   * </pre>
   *
   * <code>string tileserver_url = 3;</code>
   * @return The tileserverUrl.
   */
  java.lang.String getTileserverUrl();
  /**
   * <pre>
   * Tileserver URL.
   * </pre>
   *
   * <code>string tileserver_url = 3;</code>
   * @return The bytes for tileserverUrl.
   */
  com.google.protobuf.ByteString
      getTileserverUrlBytes();

  /**
   * <pre>
   * Map attribution.
   * </pre>
   *
   * <code>string map_attribution = 4;</code>
   * @return The mapAttribution.
   */
  java.lang.String getMapAttribution();
  /**
   * <pre>
   * Map attribution.
   * </pre>
   *
   * <code>string map_attribution = 4;</code>
   * @return The bytes for mapAttribution.
   */
  com.google.protobuf.ByteString
      getMapAttributionBytes();
}
