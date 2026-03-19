import { Agent, Session } from '../src/index';

describe('Basic tests', () => {
  test('Create an Agent', () => {
    const agent = new Agent('agent-1', 'TestAgent');
    expect(agent.agentId).toBe('agent-1');
    expect(agent.name).toBe('TestAgent');
    expect(agent.sessions).toHaveLength(0);
  });

  test('Create a Session', () => {
    const session = new Session('agent-1', 'meeting');
    expect(session.type).toBe('meeting');
    expect(session.agentId).toBe('agent-1');
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

  test('Agent has workspace', () => {
    const agent = new Agent('agent-1', 'Bob', '/custom/workspace');
    expect(agent.workspace).toBe('/custom/workspace');
  });

  test('getSession by id', () => {
    const agent = new Agent('agent-1', 'Carol');
    const session = agent.createSession('chat');
    const found = agent.getSession(session.sessionId);
    expect(found).toBe(session);
  });
});
