package ua.kata.converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;

import ua.kata.model.BowlingGameId;

@Component
public class BowlingGameIdConverter extends AbstractHttpMessageConverter<BowlingGameId> {
  private final Gson gson;

  @Autowired
  public BowlingGameIdConverter(Gson gson) {
    super(MediaType.APPLICATION_JSON_UTF8);
    this.gson = gson;
  }

  @Override
  protected boolean supports(Class<?> clazz) {
    return clazz.equals(BowlingGameId.class);
  }

  @Override
  protected BowlingGameId readInternal(
      Class<? extends BowlingGameId> clazz,
      HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
    return gson.fromJson(new InputStreamReader(inputMessage.getBody()), clazz);
  }

  @Override
  protected void writeInternal(
      BowlingGameId bowlingGameId,
      HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
    outputMessage.getBody().write(gson.toJson(bowlingGameId).getBytes());
  }
}
