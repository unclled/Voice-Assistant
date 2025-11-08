import 'package:http/http.dart' as http;

class WeatherService {
  final String _apiKey = "972e78815dba11bd759d16b7d348264d";
  final String _baseUrl = 'https://api.openweathermap.org/data/2.5';

  // Метод для получения ТЕКУЩЕЙ погоды
  Future<String> getCurrentWeather(String city) async {
    final params = '?appid=$_apiKey&units=metric&lang=ru&q=$city';
    final response = await http.get(Uri.parse('$_baseUrl/weather$params'));
    if (response.statusCode == 200) {
      return response.body;
    } else {
      throw Exception('Failed to load current weather');
    }
  }

  Future<String> getForecast(String city) async {
    final params = '?appid=$_apiKey&units=metric&lang=ru&q=$city';
    final response = await http.get(Uri.parse('$_baseUrl/forecast$params'));
    if (response.statusCode == 200) {
      return response.body;
    } else {
      throw Exception('Failed to load weather forecast');
    }
  }
}