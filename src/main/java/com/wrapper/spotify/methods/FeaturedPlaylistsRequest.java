package com.wrapper.spotify.methods;

import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.JsonUtil;
import com.wrapper.spotify.exceptions.*;
import com.wrapper.spotify.models.FeaturedPlaylists;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FeaturedPlaylistsRequest extends AbstractRequest {

  public FeaturedPlaylistsRequest(Builder builder) {
    super(builder);
  }

  /**
   * Get Featured Playlists synchronously.
   * @return Featured playlists.
   * @throws IOException In case of networking issues.
   * @throws WebApiException In case of error replies from the Web API.
   */
  public FeaturedPlaylists get() throws
          IOException,
          NoContentException,
          BadRequestException,
          UnauthorizedException,
          ForbiddenException,
          NotFoundException,
          TooManyRequestsException,
          InternalServerErrorException,
          BadGatewayException,
          ServiceUnavailableException {
    final JSONObject jsonObject = JSONObject.fromObject(getJson());
    return JsonUtil.createFeaturedPlaylist(jsonObject);
  }

  /**
   * Get Featured Playlists asynchronously.
   * @return A future that resolves to featured playlists.
   */
  public SettableFuture<FeaturedPlaylists> getAsync() {
    final SettableFuture<FeaturedPlaylists> future = SettableFuture.create();

    try {
      future.set(JsonUtil.createFeaturedPlaylist(JSONObject.fromObject(getJson())));
    } catch (Exception e) {
      future.setException(e);
    }

    return future;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder extends AbstractRequest.Builder<Builder> {

    public Builder limit(int limit) {
      assert (limit > 0);
      return setParameter("limit", String.valueOf(limit));
    }

    public Builder offset(int offset) {
      assert (offset >= 0);
      return setParameter("offset", String.valueOf(offset));
    }

    public Builder country(String countryCode) {
      assert (countryCode != null);
      return setParameter("country", countryCode);
    }

    public Builder locale(String locale) {
      assert (locale != null);
      return setParameter("locale", locale);
    }

    public Builder timestamp(Date timestamp) {
      assert (timestamp != null);
      final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
      return setParameter("timestamp", format.format(timestamp));
    }

    public Builder accessToken(String accessToken) {
      return setHeaderParameter("Authorization", "Bearer " + accessToken);
    }

    public FeaturedPlaylistsRequest build() {
      setPath("/v1/browse/featured-playlists");
      return new FeaturedPlaylistsRequest(this);
    }

  }
}
