package org.example.wmplannerbackend.services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.example.wmplannerbackend.exceptions.*;
import org.example.wmplannerbackend.interfaces.CardDto;
import org.example.wmplannerbackend.interfaces.TimeSlotDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class TimeSlotService {
    private final Firestore db = FirestoreClient.getFirestore();
    private final UserService userService;

    public TimeSlotService(UserService userService){
        this.userService = userService;
    }

    private DocumentReference getCardRef(int cardID) throws Exception {
        DocumentReference cardRef = db.collection("cards")
                .document(String.valueOf(cardID));

        DocumentSnapshot snapshot = cardRef.get().get();

        if(!snapshot.exists()){
            throw new CardNotExistException(cardID);
        }

        return cardRef;
    }

    private TimeSlotDto getTarget(List<TimeSlotDto> currentTimeslots, int timeSlotID){
        return currentTimeslots.stream()
                .filter(t -> t.id == timeSlotID)
                .findFirst()
                .orElseThrow(() -> new TimeSlotNotExistException(timeSlotID));
    }

    public TimeSlotDto addTimeSlot(int cardID, TimeSlotDto timeSlot){
        try{
            DocumentReference cardRef = getCardRef(cardID);
            DocumentSnapshot snapshot = cardRef.get().get();

            List<TimeSlotDto> currentTimeslots = Objects.requireNonNull(snapshot.toObject(CardDto.class)).timeSlots;

            List<Integer> timeslotIDs = currentTimeslots.stream()
                    .map(t-> t.id)
                    .toList();

            if(timeslotIDs.contains(timeSlot.id)){
                throw new TimeSlotAlreadyExistException(timeSlot.id);
            }

            currentTimeslots.add(timeSlot);

            cardRef.update("timeSlots", currentTimeslots).get();
            return timeSlot;
        } catch (TimeSlotAlreadyExistException | CardNotExistException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException("Failed to create Timeslot: %s".formatted(e.getMessage()));
        }
    }

    public TimeSlotDto renameTimeSlot(int cardID, TimeSlotDto timeSlot){
        try{
            DocumentReference cardRef = getCardRef(cardID);
            DocumentSnapshot snapshot = cardRef.get().get();

            List<TimeSlotDto> currentTimeslots = Objects.requireNonNull(snapshot.toObject(CardDto.class)).timeSlots;

            TimeSlotDto target = currentTimeslots.stream()
                    .filter(t -> t.id == timeSlot.id)
                    .findFirst()
                    .orElseThrow(() -> new TimeSlotNotExistException(timeSlot.id));

            target.time = timeSlot.time;

            cardRef.update("timeSlots", currentTimeslots).get();
            return timeSlot;
        } catch (TimeSlotNotExistException | CardNotExistException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException("Failed to rename Timeslot: %s".formatted(e.getMessage()));
        }
    }

    public void deleteTimeSlot(int cardID, int timeSlotID){
        try{
            DocumentReference cardRef = getCardRef(cardID);
            DocumentSnapshot snapshot = cardRef.get().get();

            List<TimeSlotDto> currentTimeslots = Objects.requireNonNull(snapshot.toObject(CardDto.class)).timeSlots;

            TimeSlotDto target = getTarget(currentTimeslots, timeSlotID);

            currentTimeslots.remove(target);
            cardRef.update("timeSlots", currentTimeslots).get();
        } catch (TimeSlotNotExistException | CardNotExistException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException("Failed to delete Timeslot: %s".formatted(e.getMessage()));
        }
    }

    public TimeSlotDto addUser(int cardID, int timeSlotID, int userID){
        try{
            DocumentReference cardRef = getCardRef(cardID);
            DocumentSnapshot snapshot = cardRef.get().get();

            List<Integer> userIDs = this.userService.getAllUsers().stream()
                    .map(u -> u.id)
                    .toList();

            if(!userIDs.contains(userID)){
                throw new UserNotExistException(userID);
            }

            List<TimeSlotDto> currentTimeslots = Objects.requireNonNull(snapshot.toObject(CardDto.class)).timeSlots;
            TimeSlotDto target = getTarget(currentTimeslots, timeSlotID);

            if(target.userIDs == null){
                target.userIDs = new ArrayList<>();
            }

            if(target.userIDs.contains(userID)){
                throw new UserAlreadyExistException(userID);
            }

            target.userIDs.add(userID);

            cardRef.update("timeSlots", currentTimeslots).get();
            return target;
        } catch (TimeSlotNotExistException |
                 CardNotExistException |
                 UserAlreadyExistException |
                 UserNotExistException e
        ) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException("Failed to add User to Timeslot: %s".formatted(e.getMessage()));
        }
    }

    public TimeSlotDto removeUser(int cardID, int timeSlotID, int userID){
        try{
            DocumentReference cardRef = getCardRef(cardID);
            DocumentSnapshot snapshot = cardRef.get().get();

            List<TimeSlotDto> currentTimeslots = Objects.requireNonNull(snapshot.toObject(CardDto.class)).timeSlots;
            TimeSlotDto target = getTarget(currentTimeslots, timeSlotID);

            if(target.userIDs != null && !target.userIDs.isEmpty()){
                target.userIDs.remove((Integer) userID);
            }

            cardRef.update("timeSlots", currentTimeslots).get();
            return target;
        } catch (TimeSlotNotExistException | CardNotExistException | UserNotExistException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException("Failed to remove User from Timeslot: %s".formatted(e.getMessage()));
        }
    }
}
