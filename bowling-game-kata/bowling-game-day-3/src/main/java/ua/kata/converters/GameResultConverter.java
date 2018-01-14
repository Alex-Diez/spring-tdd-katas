package ua.kata.converters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;

import ua.kata.model.GameResult;

@Component
public class GameResultConverter extends AbstractHttpMessageConverter<GameResult> {
  private static final Gson GSON = new GsonBuilder().create();

  public GameResultConverter() {
    super(MediaType.APPLICATION_JSON_UTF8);
  }

  @Override
  protected boolean supports(Class<?> clazz) {
    return clazz.equals(GameResult.class);
  }

  @Override
  protected GameResult readInternal(
    Class<? extends GameResult> clazz,
    HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
    return GSON.fromJson(new InputStreamReader(inputMessage.getBody()), clazz);
  }

  @Override
  protected void writeInternal(
    GameResult gameResult,
    HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
    outputMessage.getBody().write(GSON.toJson(gameResult).getBytes());
  }
}
