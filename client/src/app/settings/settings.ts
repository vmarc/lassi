export class Settings {

  framesPerSecond: number;
  fadeTimeInSeconds: number;
  buttonPageCount: number;

  constructor(framesPerSecond: number, fadeTimeInSeconds: number, buttonPageCount: number) {
    this.framesPerSecond = framesPerSecond;
    this.fadeTimeInSeconds = fadeTimeInSeconds;
    this.buttonPageCount = buttonPageCount;
  }

}
