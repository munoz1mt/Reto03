package ciclo3.reto3.service;


import ciclo3.reto3.model.Reservation;
import ciclo3.reto3.reports.CountClient;
import ciclo3.reto3.reports.ReservationStatus;
import ciclo3.reto3.repository.MessageRepository;
import ciclo3.reto3.repository.ReservationRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    public List<Reservation> getAll(){
        return reservationRepository.getAll();        
    }
    
    public Optional<Reservation> getReservation(int idReservation){
        return reservationRepository.getReservation(idReservation);
    }
    
    public Reservation save(Reservation reservation) {
        if (reservation.getIdReservation() == null) {
            return reservationRepository.save(reservation);
        } else {
            Optional<Reservation> tmpReservation = reservationRepository.getReservation(reservation.getIdReservation());
            if (tmpReservation.isEmpty()) {
                return reservationRepository.save(reservation);
            } else {
                return reservation;
            }
        }  
    }
    
    public Reservation update(Reservation reservation) {
        if (reservation.getIdReservation() != null) {
            Optional<Reservation> tmpReservation = reservationRepository.getReservation(reservation.getIdReservation());
            if (!tmpReservation.isEmpty()) {
                return reservationRepository.save(reservation);
            }
        }
        return reservation;
    }
    
    public boolean deleteReservation(int id) {
        Boolean result = getReservation(id).map(reservation -> {
            reservationRepository.delete(reservation);
            return true;
        }).orElse(false);
        return result;
    }
    
    public ReservationStatus getReservationStatusReport(){
        List<Reservation>completed=reservationRepository.getReservationByStatus("completed");
        List<Reservation>cancelled=reservationRepository.getReservationByStatus("cancelled");
        return new ReservationStatus(completed.size(), cancelled.size());
    }

    public List<Reservation> getReservationPeriod(String dateA, String dateB){
        SimpleDateFormat parser=new SimpleDateFormat("yyyy-MM-dd");
        Date aDate= new Date();
        Date bDate= new Date();

        try {
            aDate = parser.parse(dateA);
            bDate = parser.parse(dateB);
        }catch(ParseException evt){
            evt.printStackTrace();
        }
        if(aDate.before(bDate)){
            return reservationRepository.getReservationPeriod(aDate, bDate);
        }else{
            return new ArrayList<>();
        }


    }

    public List<CountClient> getTopClients(){

        return reservationRepository.getTopClients();
    }

}
