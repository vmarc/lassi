export class SceneStatusesMessage {

  constructor(readonly statusses: Array<boolean>) {
  }

  public static fromJSON(jsonObject): SceneStatusesMessage {
    if (!jsonObject) {
      return undefined;
    }
    return new SceneStatusesMessage(jsonObject.statuses);
  }

}
