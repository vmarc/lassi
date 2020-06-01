export class Settings {

  framesPerSecond: number;
  fadeTimeInSeconds: number;
  buttonPages: number;

  constructor(framesPerSecond: number, fadeTimeInSeconds: number, buttonPages: number) {
    this.framesPerSecond = framesPerSecond;
    this.fadeTimeInSeconds = fadeTimeInSeconds;
    this.buttonPages = buttonPages;
  }

}
