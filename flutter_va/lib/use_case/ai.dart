import 'dart:convert';
import 'package:intl/intl.dart';

import '../domain/weather.dart';
import '../network/weather_service.dart';

class AI {
  final WeatherService _weatherService = WeatherService();

  Future<String> getAnswer(String question) async {
    question = question.toLowerCase().trim();

    if (question.startsWith('погода на 3 дня')) {
      String city = _parseCity(question, 'погода на 3 дня');
      if (city.isEmpty) return 'Пожалуйста, укажите город для прогноза.';
      return _getWeatherForecast(city);
    } else if (question.startsWith('погода')) {
      String city = _parseCity(question, 'погода');
      if (city.isEmpty) return 'Пожалуйста, укажите город.';
      return _getCurrentWeather(city);
    } else if (question.contains('привет')) {
      return 'Привет!';
    } else if (question.contains('как дела')) {
      return 'Отлично, спасибо, что спросили!';
    } else if (question.contains('что ты умеешь')) {
      return 'Я могу отвечать на простые вопросы и показывать погоду. Спроси: "погода Москва" или "погода на неделю Санкт-Петербург".';
    } else {
      return 'Ммм, я не уверен, что понимаю.';
    }
  }

  String _parseCity(String query, String command) {
    return query.replaceFirst(command, '').trim();
  }

  Future<String> _getCurrentWeather(String city) async {
    try {
      final jsonString = await _weatherService.getCurrentWeather(city);
      final data = json.decode(jsonString);
      final weather = CurrentWeather(
          data['main']['temp'].toDouble(), data['weather'][0]['description']);
      return 'Сейчас в городе $city ${weather.temperature.round()}°C, ${weather.description}.';
    } catch (e) {
      return 'Не удалось получить погоду для города $city. Проверьте название.';
    }
  }

  Future<String> _getWeatherForecast(String city) async {
    try {
      final jsonString = await _weatherService.getForecast(city);
      final data = json.decode(jsonString);
      final List<dynamic> forecastList = data['list'];

      final Map<String, DailyForecast> dailyForecasts = {};
      for (var item in forecastList) {
        final date = DateTime.fromMillisecondsSinceEpoch(item['dt'] * 1000);
        // Берем прогноз на 15:00 как основной для дня
        if (date.hour == 15) {
          final dayKey = DateFormat('yyyy-MM-dd').format(date);
          dailyForecasts[dayKey] = DailyForecast(
            date: date,
            temp: item['main']['temp'].toDouble(),
            description: item['weather'][0]['description'],
          );
        }
      }

      if (dailyForecasts.isEmpty) return 'Не удалось составить прогноз для города $city.';

      var result = 'Прогноз погоды в городе $city на 3 дня:\n';
      final DateFormat formatter = DateFormat('d MMMM, EEEE', 'ru');
      dailyForecasts.values.take(3).forEach((forecast) {
        result += '\n${formatter.format(forecast.date)}:\n';
        result += '  Температура: ${forecast.temp.round()}°C, ${forecast.description}.';
      });

      return result;
    } catch (e) {
      return 'Не удалось получить прогноз для города $city.';
    }
  }
}