package com.example.inglih;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity8 extends AppCompatActivity {

    private String[] englishGuideItems = {
            "Глаголы: Времена (Present, Past, Future)",
            "Настоящее Простое Время (Present Simple Tense)",
            "Прошедшее Простое Время (Past Simple Tense)",
            "Будущее Простое Время (Future Simple Tense)",
            "Неправильные Глаголы",
            "Формы глагола to be (am, is, are, was, were)",
            "Модальные Глаголы (can, could, may, might, must, shall, should, will, would)",
            "Наречия времени (yesterday, today, tomorrow, etc.)",
            "Притяжательные местоимения (my, your, his, her, its, our, their)",
            "Отрицательные формы (not, don't, doesn't, didn't, won't, etc.)",
            "Вопросительные формы (do, does, did, will, can, could, etc.)",
            "Пассивный Залог (Passive Voice)",
            "Прямая и Косвенная Речь",
            "Условные Предложения (Zero, First, Second, Third Conditionals)",
            "Артикли (a, an, the)",
            "Фразовые Глаголы (Phrasal Verbs)",
            "Предлоги (Prepositions)",
            "Относительные Придаточные Предложения (Relative Clauses)",
            "Инфинитив и Герундий (Infinitive and Gerund)",
            "Определения (Adjectives)",
            "Наречия (Adverbs)",
            "Сравнение Прилагательных и Наречий (Comparative and Superlative Forms)"
            // Добавьте сюда еще важные темы английского языка, которые могут понадобиться при прохождении теста
    };

    private String[] englishGuideDetails = {
            // Глаголы: Времена (Present, Past, Future)
            "В английском языке существует несколько времен: настоящее, прошедшее и будущее. " +
                    "Например, 'I eat' (настоящее), 'I ate' (прошедшее), 'I will eat' (будущее).",
            // Настоящее Простое Время (Present Simple Tense)
            "Используется для описания регулярных действий, привычек и фактов. " +
                    "Например, 'She sings beautifully' (Она поет красиво).",
            // Прошедшее Простое Время (Past Simple Tense)
            "Используется для описания завершенных действий в прошлом. " +
                    "Например, 'I visited Paris last summer' (Я посетил Париж прошлым летом).",
            // Будущее Простое Время (Future Simple Tense)
            "Используется для выражения действий, которые произойдут в будущем. " +
                    "Например, 'They will arrive tomorrow' (Они прибудут завтра).",
            // Неправильные Глаголы
            "Это глаголы, у которых формы в прошедшем времени или причастия отличаются от стандартных правил. " +
                    "Например, 'go' (прошедшее время 'went'), 'eat' (прошедшее время 'ate').",
            // Формы глагола to be (am, is, are, was, were)
            "Глагол 'to be' используется для выражения состояния или идентификации. " +
                    "Например, 'He is a doctor' (Он врач).",
            // Модальные Глаголы (can, could, may, might, must, shall, should, will, would)
            "Эти глаголы используются для выражения различных модальностей: возможности, разрешения, необходимости и т. д. " +
                    "Например, 'You must study for the exam' (Ты должен учиться к экзамену).",
            // Наречия времени (yesterday, today, tomorrow, etc.)
            "Это слова, указывающие на точное время, когда происходит действие. " +
                    "Например, 'We will meet tomorrow' (Мы встретимся завтра).",
            // Притяжательные местоимения (my, your, his, her, its, our, their)
            "Используются для обозначения принадлежности. " +
                    "Например, 'This is my book' (Это моя книга).",
            // Отрицательные формы (not, don't, doesn't, didn't, won't, etc.)
            "Формы, используемые для отрицания утверждений. " +
                    "Например, 'She doesn't like coffee' (Она не любит кофе).",
            // Вопросительные формы (do, does, did, will, can, could, etc.)
            "Формы, используемые для образования вопросов. " +
                    "Например, 'Do you speak English?' (Ты говоришь по-английски?).",
            // Пассивный Залог (Passive Voice)
            "Форма глагола, используемая для передачи действия, а не действующего лица. " +
                    "Например, 'The book was written by Mark Twain' (Книга была написана Марком Твеном).",
            // Прямая и Косвенная Речь
            "Способы передачи чужой речи в английском языке. " +
                    "Например, Прямая речь: 'She said, 'I am tired''' (Она сказала: 'Я устала'). " +
                    "Косвенная речь: 'She said that she was tired' (Она сказала, что она устала).",
            // Условные Предложения (Zero, First, Second, Third Conditionals)
            "Предложения, которые выражают условие и его возможное следствие. " +
                    "Например, Zero Conditional: 'If you heat ice, it melts' (Если ты нагреешь лед, он растает). " +
                    "First Conditional: 'If it rains, we will stay at home' (Если будет дождь, мы останемся дома).",
            // Артикли (a, an, the)
            "Слова, используемые для указания на отношения между объектами в предложении. " +
                    "Например, 'She gave me a book' (Она дала мне книгу).",
            // Фразовые Глаголы (Phrasal Verbs)
            "Глаголы, которые образуются с помощью комбинации глагола и предлога или наречия. " +
                    "Например, 'She looks after her sister' (Она присматривает за своей сестрой).",
            // Предлоги (Prepositions)
            "Слова, используемые для указания на отношения между объектами в предложении. " +
                    "Например, 'The book is on the table' (Книга на столе).",
            // Относительные Придаточные Предложения (Relative Clauses)
            "Предложения, которые дополняют основное предложение, предоставляя дополнительную информацию о существительном. " +
                    "Например, 'The man who is sitting there is my friend' (Человек, который сидит там, мой друг).",
            // Инфинитив и Герундий (Infinitive and Gerund)
            "Формы глагола, используемые как существительные или для выражения цели. " +
                    "Например, 'I like swimming' (Мне нравится плавание).",
            // Определения (Adjectives)
            "Слова, используемые для описания или уточнения существительного. " +
                    "Например, 'She is a beautiful girl' (Она красивая девушка).",
            // Наречия (Adverbs)
            "Слова, используемые для описания действия, прилагательного или другого наречия. " +
                    "Например, 'He runs quickly' (Он быстро бежит).",
            // Сравнение Прилагательных и Наречий (Comparative and Superlative Forms)
            "Формы, используемые для сравнения качеств или действий. " +
                    "Например, 'She is taller than her sister' (Она выше своей сестры)."
            // Добавьте дополнительные пояснения для новых тем английского языка
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        ListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, englishGuideItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = englishGuideItems[position];
            String details = englishGuideDetails[position];
            Intent intent = new Intent(MainActivity8.this, MainActivity9.class);
            intent.putExtra("selectedItem", selectedItem);
            intent.putExtra("details", details);
            startActivity(intent);
        });

        findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}