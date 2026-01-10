export class Point {

  private readonly _x: string
  private readonly _y: string
  private readonly _r: string
  private readonly _status: string
  private readonly _currentTime: string
  private readonly _executionTime: string

  constructor(x: string, y: string, r: string, status: string, currentTime: string, executionTime: string) {
    this._x = x
    this._y = y
    this._r = r
    this._status = status
    this._currentTime = currentTime
    this._executionTime = executionTime
  }


  get x(): string {
    return Number(this._x).toFixed(2)
  }

  get y(): string {
    return Number(this._y).toFixed(2)
  }

  get r(): string {
    return this._r
  }

  get status(): string {
    return this._status
  }

  get currentTime(): string {
    return this._currentTime
  }

  get executionTime(): string {
    return this._executionTime
  }
}
