import { Agent, Session } from '../src';

describe('Basic Test', () => {
  test('should create an Agent', () => {
    const agent = new Agent('TestAgent');
    expect(agent.name).toBe('TestAgent');
    expect(agent.sessions).toEqual([]);
  });

  test('should create a Session', () => {
    const session = new Session('test');
    expect(session.type).toBe('test');
  });

  test('should call process and return string', () => {
    const session = new Session('test');
    const result = session.process('hello');
    expect(typeof result).toBe('string');
    expect(result).toContain('hello');
  });

  test('should create session from Agent', () => {
    const agent = new Agent('TestAgent');
    const session = agent.createSession('meeting');
    expect(session).toBeInstanceOf(Session);
    expect(session.type).toBe('meeting');
    expect(agent.sessions).toHaveLength(1);
  });
});