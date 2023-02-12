import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  noOfRounds$: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  playerName$: BehaviorSubject<string> = new BehaviorSubject<string>('');
  gameOver$: BehaviorSubject<Boolean> = new BehaviorSubject<Boolean>(false);
  winCounter$: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  lossCounter$: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  constructor() { }

  setNoOfRounds(num: number) {
    this.noOfRounds$.next(num);
  }

  getNoOfRounds(): Observable<Number> {
    return this.noOfRounds$.asObservable();
  }

  setCurrentPlayer(name: string) {
    this.playerName$.next(name)
  }

  getCurrentPlayer(): Observable<string> {
    return this.playerName$.asObservable();
  }

  setGameOver(status: Boolean) {
    this.gameOver$.next(status)
  }

  getGameOver(): Observable<Boolean> {
    return this.gameOver$.asObservable();
  }

  setWinCounter(num: number) {
    this.winCounter$.next(num);
  }

  getWinCounter(): Observable<Number> {
    return this.winCounter$.asObservable();
  }

  setLossCounter(num: number) {
    this.lossCounter$.next(num);
  }

  getLossCounter(): Observable<Number> {
    return this.lossCounter$.asObservable();
  }
}
