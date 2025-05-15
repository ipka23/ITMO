    ORG 0x20
ARR_LENGTH:    WORD ?  ; Длина вводимого массива
CURR_ELEM:    WORD 0  ; Текущий элемент
ELEMS_LEFT:    WORD ?  ; Сколько элементов осталось (счетчик цикла)
SUM_0_15:    WORD 0  ; Результат/сумма (младшие 2 байта)
SUM_16_31:    WORD 0  ; Результат/сумма (старшие 2 байта)
INDEX_CHECK:    WORD 0  ; Счетчик, который обнуляется каждый k-ый индекс (для суммы k-ых элементов)
INDEX_INDENT:  WORD ?  ; k - число, которому кратны индексы суммируемых элементов
COMPARISON_MASK:  WORD 0x2000 ; Маска для проверки знака 14-ти разрядного числа
NEGATIVE_MASK:  WORD 0xC000 ; Маска для очищения "мусора" в старших разрядах и получения отрицательного числа
POSITIVE_MASK:  WORD 0x1FFF  ; Маска для очищения "мусора" в старших разрядах и получения положительного числа

START:  CLA    ; Загружаем исходные данные в обновляемые ячейки и обнуляем ячейки результата
  ST $SUM_0_15
  ST $SUM_16_31
  LD $ARR_LENGTH
  ST $ELEMS_LEFT

MAIN_LOOP:  CLA    ; Основной цикл программы
  ST $CURR_ELEM  ; Обнуляем ячейку с предыдущим считанным элементом
  CALL WORD_INPUT  ; Вызов подпрограммы ввода числа
  LD $INDEX_CHECK  ; Проверка на равенство счетчика числу k
  INC
  CMP $INDEX_INDENT
  BEQ FUNCTION  ; Если счетчик равен k - переходим к функции суммирования
  ST $INDEX_CHECK  ; Иначе - сохраняем новое значение счетчика и продолжаем цикл
LOOP_END:  LOOP ELEMS_LEFT
  JUMP MAIN_LOOP
  HLT

WORD_INPUT:      ; Подпрограмма для ввода числа с ВУ-2 (сначала старший байт, затем младший)
SPIN_LOOP_1:  IN 5    ; Спин-луп
  AND #0x40
  BEQ SPIN_LOOP_1
  IN 4    ; Ввод старшего байта
  SWAB
  ST $CURR_ELEM
SPIN_LOOP_2:  IN 5    ; Спин-луп
  AND #0x40
  BEQ SPIN_LOOP_2
  IN 4    ; Ввод младшего байта
  ADD $CURR_ELEM
  ST $CURR_ELEM
  RET

FUNCTION:  CLA
  ST $INDEX_CHECK  ; Обнуляем счетчик элементов
  LD $CURR_ELEM  ; Загружаем только что считанный элемент
  AND $COMPARISON_MASK  ; Проверяем знак числа с помощью маски и переходим к нужной подфункции суммирования
  BEQ POS_ELEM
  JUMP NEG_ELEM

POS_ELEM:  LD $CURR_ELEM  ; Подфункция для суммирования с положительным числом
  AND $POSITIVE_MASK
  ADD $SUM_0_15
  ST $SUM_0_15
  CLA
  ADC $SUM_16_31
  ST $SUM_16_31
  JUMP LOOP_END

NEG_ELEM:  LD $CURR_ELEM  ; Подфункция для суммирования с отрицательным числом
  OR $NEGATIVE_MASK
  ADD $SUM_0_15
  ST $SUM_0_15
  LD #0xFF
  ADC $SUM_16_31
  ST $SUM_16_31
  JUMP LOOP_END
