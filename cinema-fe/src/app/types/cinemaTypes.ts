export interface Film {
  id: number;
  name: string;
  description: string;
  releaseDate: string;
  durationInRoom: number;
  price?: number;
  minutes: number;
  imageLink: string;
}

export interface Theater {
  id: number;
  name: string;
  technology: string;
  capacity: number;
  price: number;
}

export interface ShowTime {
  id: number;
  startTime: string;
  scheduleId: number;
  endTime: string;
  occupiedSeats: number;
  schedule?: Schedule;
}

export interface Schedule {
  id: number;
  film: Film;
  theater: Theater;
  startDateTime: string;
  endDateTime: string;
  showTimes: ShowTime[];
}
