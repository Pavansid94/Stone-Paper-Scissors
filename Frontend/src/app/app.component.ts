import { Component, Inject, OnInit } from '@angular/core';
import { BackendService } from './backend.service';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { LocalStorageService } from './local-storage.service';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { GameService } from './game.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  computer: any = null;
  playerChoice: any = null;
  playerName: string = '';
  computerName: string = '';
  result: any = null;
  recentMatchups: string = '';
  returnVal = '';
  winCounter: Number = 0;
  lossCounter: Number = 0;
  noOfRounds: Number;

  constructor(
    private backendService: BackendService,
    private dialog: MatDialog,
    private localStorageService: LocalStorageService,
    private gameService: GameService
  ) {
    //set|subscribe some variables
    this.gameService.setGameOver(false);

    this.gameService.getWinCounter().subscribe((val) => {
      this.winCounter = val;
    })

    this.gameService.getLossCounter().subscribe((val) => {
      this.lossCounter = val;
    })
  }

  ngOnInit(): void {
    //load the user form 
    this.openDialog()
  }

  openDialog(): void {
    let dialogRef = this.dialog.open(UserDetailDialog, {
      width: '350px', disableClose: true
    });

    dialogRef.afterClosed().subscribe(result => {
      //set some variables out of the form 
      this.computerName = this.localStorageService.getData("computerName")
      this.gameService.getCurrentPlayer().subscribe((value) => {
        this.playerName = value;
      })
      this.gameService.getComputer().subscribe((value) => {
        this.computerName = value
      })
      this.gameService.getNoOfRounds().subscribe((value) => {
        this.noOfRounds = value;
      })
    });
  }



  setPlayerChoice(choice: string) {
    this.playerChoice = choice;
    let gameId = this.localStorageService.getData("gameId")
    this.recentMatchups = ''

    this.backendService.playerChoice(Number(gameId), this.playerChoice).subscribe(
      (response: any) => {
        //obtain previous match results and choices
        let lastGame = response.rounds[(response.rounds.length) - 1];
        this.computer = lastGame.p2Choice;
        this.result = lastGame.p1Result;
        //using the following for 'All recent results' section
        response.rounds.reverse().forEach((item: { p1Result: string; }) => {
          this.recentMatchups = this.recentMatchups + (item.p1Result == 'WIN' ? 'W ' : (item.p1Result == 'LOSS' ? 'L ' : 'T '));
        });
        this.gameService.setWinCounter(this.recentMatchups.split(" ").filter(val => val == 'W').length);
        this.gameService.setLossCounter(this.recentMatchups.split(" ").filter(val => val == 'L').length);
      },
      (error) => {
        //set Game as over and initialize the dialog once again
        if (error['status'] == 400) {
          this.gameService.setGameOver(true)
          this.openDialog()
        }
      }
    )
  }


}

//Component for user dialog
@Component({
  templateUrl: './user-detail-dialog.html',
})
export class UserDetailDialog {
  constructor(public dialogRef: MatDialogRef<UserDetailDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private backendService: BackendService,
    private localStorageService: LocalStorageService,
    private gameService: GameService,
    private fb: FormBuilder) {
    //Using form with simple Validations	
    this.form = fb.group({
      number: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
      text: ['', [Validators.required, Validators.pattern("^[a-zA-Z0-9]*$")]],
      newGame: new FormControl(true),
      unlimitedRounds: new FormControl({ value: false, disabled: this.gameOver })
    })

    //get some variables from observables
    this.gameService.getCurrentPlayer().subscribe((value) => {
      this.form.controls['text'].reset(value)
    })
    this.gameService.getNoOfRounds().subscribe((value) => {
      this.form.controls['number'].reset(value)
    })
    this.gameService.getGameOver().subscribe((val: any) => {
      this.gameOver = val;
      this.form.controls['unlimitedRounds'].reset({ value: !val, disabled: this.gameOver })
    })
  }

  form: FormGroup = new FormGroup({});
  gameOver: any;
  newGame = true

  get formControls() {
    return this.form.controls;
  }

  setTheGame() {

    if (this.form.value.newGame) {
      if (this.form.value.number > 0 && !this.form.controls.unlimitedRounds.value) {
        //if new game and noOfRounds are defined
        this.backendService.setTheGame(this.form.value.text, this.form.value.number).subscribe(
          (response: any) => {
            //set|update some variables
            this.setApplicationVariables(response);
            this.initializeCounters();
          })
      }
      else {
        //if new game and unlimited noOfRounds
        this.backendService.setTheGame(this.form.value.text).subscribe(
          (response: any) => {
            //set|update some variables
            this.setApplicationVariables(response);
            this.initializeCounters();
          })
      }
    }
    else {
      //if previous game and noOfRounds set to 0 -> unlimted noOfRounds -> corner case
      if (this.form.value.number == 0) {
        this.form.value.number = 9999
      }
      let gameId: Number = Number(this.localStorageService.getData("gameId"))
      this.backendService.increaseRounds(gameId, this.form.value.number).subscribe(
        (response: any) => {
          //set|update some variables
          this.setApplicationVariables(response);
        })
    }
  }

  //helper methods
  private initializeCounters() {
    this.gameService.setWinCounter(0);
    this.gameService.setLossCounter(0);
  }

  private setApplicationVariables(response: any) {
    this.localStorageService.saveData("gameId", response.id.toString());
    this.gameService.setComputer(response.playerTwo)
    this.gameService.setCurrentPlayer(response.playerOne)
    this.gameService.setNoOfRounds(response.noOfRounds)
  }
}
