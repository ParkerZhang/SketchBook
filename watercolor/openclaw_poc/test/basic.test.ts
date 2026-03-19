import { Agent, Session } from '../src/index';

describe('Basic tests', () => {
  test('Create an Agent', () => {
    const agent = new Agent('agent-1', 'TestAgent');
    expect(agent.name).toBe('TestAgent');
    expect(agent.sessions).toHaveLength(0);
  });

  test('Create a Session', () => {
    const session = new Session('agent-1', 'meeting');
    expect(session.type).toBe('meeting');
  });

  test('Call process() returns a string', () => {
    const session = new Session('agent-1', 'test');
    const result = session.process('hello');
    expect(typeof result).toBe('string');
    expect(result).toBe('Processed: hello');
  });

  test('Agent creates and stores sessions', () => {
    const agent = new Agent('agent-1', 'Alice');
    const session = agent.createSession('chat');
    expect(agent.sessions).toHaveLength(1);
    expect(session.type).toBe('chat');
  });
});
