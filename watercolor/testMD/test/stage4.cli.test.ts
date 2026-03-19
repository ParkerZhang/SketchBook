import { MeetingEngine } from '../sandbox/MeetingEngine';
import { CLI } from '../sandbox/CLI';

describe('Stage 4 - CLI Parsing and External Command Tests', () => {
  let engine: MeetingEngine;
  let cli: CLI;

  beforeEach(() => {
    engine = new MeetingEngine();
    cli = new CLI(engine);
  });

  describe('CLI.parse()', () => {
    test('parses start command', () => {
      expect(CLI.parse('start "ABC"')).toEqual({ type: 'start', subject: 'ABC' });
    });

    test('parses stop command', () => {
      expect(CLI.parse('stop "ABC"')).toEqual({ type: 'stop', subject: 'ABC' });
    });

    test('parses resume command', () => {
      expect(CLI.parse('resume "ABC"')).toEqual({ type: 'resume', subject: 'ABC' });
    });

    test('parses note command', () => {
      expect(CLI.parse('note "ABC" "Hello"')).toEqual({
        type: 'note',
        subject: 'ABC',
        text: 'Hello'
      });
    });

    test('parses end command', () => {
      expect(CLI.parse('end "ABC"')).toEqual({ type: 'end', subject: 'ABC' });
    });

    test('throws on invalid format', () => {
      expect(() => CLI.parse('invalid')).toThrow('Invalid command format');
    });
  });

  describe('handleExternalCommand()', () => {
    test('creates meeting via external command', () => {
      engine.handleExternalCommand('start "Demo"');
      const meeting = engine.getMeeting('Demo');
      expect(meeting).toBeDefined();
    });

    test('full flow via external commands', () => {
      engine.handleExternalCommand('start "Flow"');
      engine.handleExternalCommand('note "Flow" "Testing"');
      engine.handleExternalCommand('end "Flow"');

      expect(engine.getMeeting('Flow')).toBeUndefined();
    });

    test('note command adds note', () => {
      engine.handleExternalCommand('start "Test"');
      engine.handleExternalCommand('note "Test" "Important note"');
      
      const meeting = engine.getMeeting('Test');
      expect(meeting?.notes).toContain('Important note');
    });
  });
});
