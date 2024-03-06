package com.lascaux.cinema;


import com.lascaux.cinema.models.*;
import com.lascaux.cinema.repositories.FilmRepository;
import com.lascaux.cinema.repositories.ScheduleRepository;
import com.lascaux.cinema.repositories.ShowTimeRepository;
import com.lascaux.cinema.repositories.TheaterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.util.*;


@Configuration
@Slf4j
public class Conf {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    TheaterRepository theaterRepository;


    @Autowired
    private ShowTimeRepository showTimeRepository;

    public static List<Film> generateFilms(int numFilms) {
        List<Film> films = new ArrayList<>();
        Random random = new Random();

        List<String> filmsImages = new ArrayList<String>();
        filmsImages.add("https://m.media-amazon.com/images/I/A1u+2fY5yTL._AC_UF1000,1000_QL80_.jpg");
        filmsImages.add("https://i.ebayimg.com/images/g/opoAAOSwJRxhvC4V/s-l1600.jpg");
        filmsImages.add("https://pad.mymovies.it/filmclub/2014/04/113/locandina.jpg");
        filmsImages.add("https://upload.wikimedia.org/wikipedia/commons/8/8c/Il_Muro_-_FIlm_2019_-_Locandina.jpg");
        filmsImages.add("https://pad.mymovies.it/filmclub/2019/03/227/locandina.jpg");

        filmsImages.add("https://pad.mymovies.it/filmclub/2019/03/227/locandina.jpg");
        filmsImages.add("https://i.ebayimg.com/images/g/ejMAAOSwo3diLgD-/s-l1600.jpg");
        filmsImages.add("https://pad.mymovies.it/filmclub/2022/03/108/locandinapg1.jpg");
        filmsImages.add("https://www.cinematographe.it/wp-content/uploads/2019/08/TIMFAmore_Artwork.jpg");
        filmsImages.add("https://lh3.googleusercontent.com/proxy/4534fmEblipPmXsBhmHgJNBwK_FN2KqrX5MWHUfs3WGNXX978b95YYb0QM6SyTdqUW42CdQ_xjvwU4cHOL7dUCtGo-O84O0FFuC6_lfKn16SJadE9srWAPtWqhKf476QhSznMogbBHWPw_dt7jinGXwdNkcsmZWiybE2IA");
        filmsImages.add("https://www.ilpost.it/wp-content/uploads/2017/12/locandine09.jpg");
        filmsImages.add("https://www.voto10.it/cinema/uploads/foto/spencer-locandina.jpg");
        filmsImages.add("https://www.cinemadelsilenzio.it/images/film/poster/19403_big.jpg");

        int imageIndex = 0;
        for (int i = 0; i < numFilms; i++) {

            String name = "Film " + (i + 1);
            String description = "Descrizione del film " + (i + 1);
            Date releaseDate = new Date(); // Data di rilascio casuale

            int minutes = random.nextInt(180 - 40 + 1) + 40; // Durata in sala da 40 a 180 minuti (massimo 3 ore)

            Film film = new Film(name, description, releaseDate, minutes);
            film.setImageLink(filmsImages.get(imageIndex));
            imageIndex++;
            if (imageIndex > 12) imageIndex = 0;
            films.add(film);
        }

        return films;
    }

    @Bean
    public CommandLineRunner generateFilmSchedules() {
        return args -> {

            // Verifica se ci sono già teatri nel database
            if (theaterRepository.count() == 0) {

                log.info("generating theaters");
                // Se non ci sono teatri, generiamo e salviamo nuovi teatri
                List<Theater> theaters = generateTheaters();
                theaterRepository.saveAll(theaters);
            }

            // Verifica se ci sono già film nel database
            if (filmRepository.count() == 0) {
                log.info("generating films");
                List<Film> films = generateFilms(30);
                filmRepository.saveAll(films);
            }

            // Verifica se ci sono già programmazioni nel database
            if (scheduleRepository.count() == 0) {
                log.info("generating schedules");
                // Se non ci sono programmazioni, generiamo e salviamo nuove programmazioni
                List<Film> films = filmRepository.findAll();
                List<Theater> theaters = theaterRepository.findAll();
                List<Schedule> schedules = generateCurrentSchedules(films, theaters);
                scheduleRepository.saveAll(schedules);
            }

            if (showTimeRepository.count() == 0) {
                log.info("generating schedules");
                // Se non ci sono programmazioni, generiamo e salviamo nuove programmazioni

                List<Schedule> schedules = scheduleRepository.findAll();
                for (Schedule schedule : schedules) {

                    List<ShowTime> showTimes = generateShowTimesForSchedule(schedule);
                    System.out.println("genero gli showTime");
                    System.out.println(showTimes);
                    showTimeRepository.saveAll(showTimes);
                }

            }


        };
    }

    private List<Theater> generateTheaters() {

        List<Theater> theaters = new ArrayList<>();
        Random random = new Random();
        int imaxTheaters = 0;

        for (int i = 0; i <= 12; i++) {
            Technology technology = (imaxTheaters < 2 && random.nextBoolean()) ? Technology.IMAX : Technology.Standard;
            float price = 8.20F;
            if (technology == Technology.IMAX) {
                imaxTheaters++;
                price = 10.20F;
            }
            int capacity = random.nextInt(201) + 50; // Capacità casuale tra 50 e 250 posti


            theaters.add(new Theater("Sala " + i, capacity, technology, price));
        }
        return theaters;
    }

    public List<Schedule> generateCurrentSchedules(List<Film> films, List<Theater> theaters) {
        List<Schedule> schedules = new ArrayList<>();
        Random random = new Random();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // Impostazione al primo giorno della settimana (lunedì)

        for (Theater theater : theaters) {
            // Per ogni sala, si genera una programmazione settimanale con film casuali

            // Scelta casuale di un film dalla lista dei film disponibili
            Film film = films.get(random.nextInt(films.size()));

            // Generazione della data di inizio della proiezione (alle 5:00 PM del giorno)
            calendar.set(Calendar.HOUR_OF_DAY, 17);
            Date startDateTime = calendar.getTime();

            // Calcolo del numero casuale di settimane (da 1 a 3)
            int randomWeeks = random.nextInt(3) + 1;

            // Aggiunta del numero casuale di settimane alla data di inizio
            calendar.add(Calendar.WEEK_OF_YEAR, randomWeeks);
            Date endDateTime = calendar.getTime();

            // Creazione della programmazione per la sala corrente e il film selezionato
            Schedule schedule = new Schedule(film, theater, startDateTime, endDateTime);
            schedules.add(schedule);

            // Aggiornamento della data per il prossimo spettacolo (un giorno dopo)
            calendar.add(Calendar.DAY_OF_WEEK, 1);

        }

        return schedules;
    }


    public List<ShowTime> generateShowTimesForSchedule(Schedule schedule) {
        List<ShowTime> showTimes = new ArrayList<>();
        LocalTime startTime = LocalTime.of(17, 0); // Orario di inizio degli spettacoli (5:00 PM)
        int projectionMinutes = 0;
        int maxMinutes = 480; // Durata massima della giornata di proiezione (8 ore)
        int filmDuration = schedule.getFilm().getMinutes(); // Durata del film in minuti
        Random rand = new Random();
        while (projectionMinutes < maxMinutes) {
            LocalTime endTime = startTime.plusMinutes(filmDuration); // Calcola l'orario di fine aggiungendo la durata del film a startTime

            projectionMinutes += filmDuration + 30;
            if (projectionMinutes > maxMinutes) break;

            // Creazione di un nuovo ShowTime con l'orario di inizio e fine calcolati
            ShowTime showTime = new ShowTime();
            showTime.setStartTime(startTime);
            showTime.setEndTime(endTime);
            showTime.setSchedule(schedule);
            showTimes.add(showTime);
            int occupiedSeats = rand.nextInt(schedule.getTheater().getCapacity()) + 1;
            showTime.setOccupiedSeats(occupiedSeats);
            startTime = endTime.plusMinutes(30); // Imposta l'orario di inizio per il prossimo spettacolo aggiungendo 30 minuti
        }

        return showTimes;
    }

    
}



