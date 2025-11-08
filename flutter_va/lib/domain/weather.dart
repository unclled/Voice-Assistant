class CurrentWeather {
  final double temperature;
  final String description;

  CurrentWeather(this.temperature, this.description);
}

class DailyForecast {
  final DateTime date;
  final double temp;
  final String description;

  DailyForecast({required this.date, required this.temp, required this.description});
}