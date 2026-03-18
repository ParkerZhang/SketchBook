import { Session } from './Session';

export class Agent {
  name: string;
  sessions: Session[];

  constructor(name: string) {
    this.name = name;
    this.sessions = [];
  }

  createSession(type: string): Session {
    const session = new Session(type);
    this.sessions.push(session);
    return session;
  }
}