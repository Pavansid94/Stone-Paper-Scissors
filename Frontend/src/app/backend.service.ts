import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  url = 'http://localhost:8080/api/v1/games/'

  constructor(private http: HttpClient) {

  }

  playerChoice(gameId: Number, choice: string): Observable<any> {
    return this.http.put(this.url + gameId + "?choice=" + choice, null)
  }

  setTheGame(playerName: string, noOfRounds?: Number) {
    if (noOfRounds) {
      return this.http.post(this.url + "?playerOne=" + playerName + "&noOfRounds=" + noOfRounds, null)
    }
    else return this.http.post(this.url + "?playerOne=" + playerName, null)
  }

  increaseRounds(gameId: Number, noOfRounds: Number) {
    return this.http.put(this.url + gameId + "/rounds/" + noOfRounds, null)
  }

  testing() {
    return this.http.get(this.url)
  }
}
