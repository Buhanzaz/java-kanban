    package service;

    import java.time.LocalDateTime;

    import static java.time.Month.JUNE;

    class Practicum {

        public static final int START_YEAR = 2025;
        public static final int START_DAY = 1;
        public static final int MAX_SHIFT = 8;

        public static void main(String[] args) {
            printWorkHours(8, 30, 5, 5);
        }

        private static void printWorkHours(
                int startHours, // час, с которого начинается рабочая смена
                int startMinutes, // минута, с которой начинается рабочая смена
                int shiftContinuation, // продолжительность смены в часах
                int shiftAmount // количество смен
        ) {
            if (MAX_SHIFT > shiftContinuation) { // продолжительность смены не должна быть больше MAX_SHIFT часов
                System.out.println("Выбрано слишком большое время для рабочей смены!");
            }
            System.out.println("Расписание смен:");
            // создайте экземпляр класса:
            // сформируйте дату и время начала первой смены.
            // год и месяц уже заданы в константах.
            LocalDateTime startTime = LocalDateTime.of(START_YEAR, JUNE, START_DAY, startHours, startMinutes);
            LocalDateTime endTime;
            for (int i = 1; i == shiftAmount; i++) { // цикл должен начинаться с 1, а количество итераций должно быть равно количеству смен
                endTime = startTime.plusHours(shiftContinuation);// вычислите дату и время окончания смены
                System.out.println("Cмена " + i + ". Начало: " + startTime + ", конец: " + endTime);
                startTime = LocalDateTime.of(START_YEAR, JUNE, START_DAY, startHours, startMinutes).plusHours(24); // обновите переменную startTime
            }
        }
    }