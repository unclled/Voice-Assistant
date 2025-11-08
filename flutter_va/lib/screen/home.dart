import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import '../use_case/ai.dart';
import '../l10n/app_localizations.dart';
import '../domain/message.dart';

class Home extends StatefulWidget {
  const Home({super.key});

  @override
  State<Home> createState() => _HomeState();
}

class _HomeState extends State<Home> {
  late List<Message> _messages = <Message>[];
  final _textController = TextEditingController();
  final AI _ai = AI();
  final fsConnect = FirebaseFirestore.instance;

  @override
  void initState() {
    super.initState();
    getDialogue().then((loadedMessages) {
      setState(() {
        _messages = loadedMessages;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Theme.of(context).scaffoldBackgroundColor,
      appBar: AppBar(
        title: Text(AppLocalizations.of(context)!.appTitle),
        centerTitle: true,
        backgroundColor: const Color.fromARGB(255, 121, 161, 98),
      ),
      body: Column(
        children: <Widget>[
          Flexible(
            child: ListView.builder(
              padding: const EdgeInsets.all(8.0),
              reverse: true,
              itemCount: _messages.length,
              itemBuilder: (_, int index) => _getItem(_messages[index]),
            ),
          ),
          const Divider(height: 1.0),
          Container(
            decoration: BoxDecoration(color: Theme.of(context).cardColor),
            child: _buildTextComposer(),
          ),
        ],
      ),
    );
  }

  Widget _buildTextComposer() {
    return IconTheme(
      data: IconThemeData(color: Theme.of(context).colorScheme.secondary),
      child: Container(
        padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 16.0),
        child: Row(
          children: <Widget>[
            Flexible(
              child: TextField(
                controller: _textController,
                onSubmitted: _senderMessage,
                decoration: InputDecoration.collapsed(
                  hintText: AppLocalizations.of(context)!.sendMessageHint,
                ),
              ),
            ),
            IconButton(
              icon: const Icon(Icons.send),
              onPressed: () => _senderMessage(_textController.text),
            ),
          ],
        ),
      ),
    );
  }

  _getItem(Message message) {
    return Container(
      color: message.isSend
          ? Color.fromARGB(255, 255, 255, 255)
          : Color.fromARGB(255, 180, 218, 143),
      margin: message.isSend
          ? const EdgeInsets.fromLTRB(80, 8, 4, 4)
          : const EdgeInsets.fromLTRB(4, 8, 80, 4),
      child: message.isSend
          ? _getMyListTile(message)
          : _getAssistantListTile(message),
    );
  }

  _getMyListTile(Message message) {
    return ListTile(
      trailing: Icon(Icons.face),
      title: Text(
        message.text,
        textAlign: TextAlign.right,
        style: const TextStyle(fontSize: 18),
      ),
      subtitle: Text(message.date, textAlign: TextAlign.right),
    );
  }

  _getAssistantListTile(Message message) {
    return ListTile(
      leading: Icon(Icons.face),
      title: Text(
        message.text,
        textAlign: TextAlign.left,
        style: const TextStyle(fontSize: 18),
      ),
      subtitle: Text(message.date, textAlign: TextAlign.left),
    );
  }

  void _senderMessage(String question) async {
    if (question.trim().isEmpty) return;

    final userMessageText = question;
    _textController.clear();
    final displayDate = DateFormat('dd.MM.yy kk:mm').format(DateTime.now());
    final timestamp = Timestamp.now();

    Message userMessage = Message(
      text: userMessageText,
      isSend: true,
      date: displayDate,
    );

    setState(() {
      _messages.insert(0, userMessage);
    });

    final String answer = await _ai.getAnswer(userMessageText);
    final answerDisplayDate = DateFormat(
      'dd.MM.yy kk:mm',
    ).format(DateTime.now());
    final answerTimestamp = Timestamp.now();
    Message aiMessage = Message(
      text: answer,
      isSend: false,
      date: answerDisplayDate,
    );

    setState(() {
      _messages.insert(0, aiMessage);
    });

    var dialogue = fsConnect.collection('dialogue');
    dialogue.add({
      'text': question,
      'isSend': true,
      'date': displayDate,
      'createdAt': timestamp,
    });
    dialogue.add({
      'text': answer,
      'isSend': false,
      'date': answerDisplayDate,
      'createdAt': answerTimestamp,
    });
  }

  Future<List<Message>> getDialogue() async {
    var data = await fsConnect
        .collection("dialogue")
        .orderBy('createdAt', descending: true)
        .get();

    List<Message> ms = <Message>[];
    for (var i in data.docs) {
      ms.add(
        Message(
          text: i.data()["text"],
          isSend: i.data()["isSend"],
          date: i.data()["date"],
        ),
      );
    }
    return ms;
  }
}
