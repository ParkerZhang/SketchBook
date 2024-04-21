package scketch.anatomy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.lang.String;

import static java.nio.charset.StandardCharsets.*;


public class MemJson {
    private String _name;
    private String _file;
    private byte[][] _mem;
    private int[][] _index;


    protected static int[][] _index_cache = new int[7000000][2];

    public MemJson(String name, String[] params) throws IOException {
        _name = name;
        _file = params[0];
        int mem_size1 = Integer.parseInt(params[1]);
        int mem_size2 = Integer.parseInt(params[2]);
        _mem = new byte[mem_size1][mem_size2];
        load();

    }

    public MemJson(String name, String file, int mem_size1, int mem_size2) throws IOException {
        _name = name;
        _file = file;
        _mem = new byte[mem_size1][mem_size2];

        load();


    }

    public void load() throws IOException {
        File f = new File(_file);
        FileInputStream fis = new FileInputStream(f);

        for (int i = 0; i < _mem.length; i++) {
            long length = fis.read(_mem[i]);
            if (length <= 0 || length < _mem[0].length - 1) break;
        }

        fis.close();

        System.out.printf("\n%s:%d\n", _file, indexLines());
    }

    private long indexLines() {
        int count = 0, count1 = 0, count2 = 0;
        int length = _mem[0].length;

        boolean EOF = false;
        for (int i = 0; i < _mem.length; i++) {
            for (int j = 0; j < _mem[i].length; j++) {
                if (_mem[i][j] == '\n') // 10
                {
                    _index_cache[count][0] = i;
                    _index_cache[count][1] = j;
                    count++;
                    count1++;
                }
                if (_mem[i][j] == 13) // CR
                {
                    _index_cache[count][0] = i;
                    _index_cache[count][1] = j;
                    count++;
                    count2++;
                }
                if (_mem[i][j] == 0) { //EOF
                    _index_cache[count][0] = i;
                    _index_cache[count][1] = j;
                    count++;
                    EOF = true;
                    break;
                }
            }
            if (EOF) {
                break;
            }
        }
        if (!EOF) {
            _index_cache[count][0] = _mem.length - 1;
            _index_cache[count][1] = _mem[0].length - 1;
            count++;
        }

        long previous_newline_position = 0, next_newline_position = 0;
        int line = 0;
        for (int i = 0; i < count; i++) {
            next_newline_position = (long) _index_cache[i][0] * length + _index_cache[i][1];
            if (next_newline_position - previous_newline_position > 1) {
                line++;
            }
            previous_newline_position = next_newline_position;
        }
        _index = new int[line][4];

        previous_newline_position = -1;
        next_newline_position = 0;
        line = 0;
        for (int i = 0; i < count; i++) {
            next_newline_position = (long) _index_cache[i][0] * length + _index_cache[i][1];
            if (next_newline_position - previous_newline_position > 1) {
                _index[line][0] = (int) ((previous_newline_position + 1) / length);
                _index[line][1] = (int) ((previous_newline_position + 1) % length);
                _index[line][2] = (int) ((next_newline_position - 1) / length);
                _index[line][3] = (int) ((next_newline_position - 1) % length);
                line++;
            }
            previous_newline_position = next_newline_position;
        }
        return line;
    }

    public String get(int idx) {
        if (idx >= _index.length) {
            return null;
        }
        int length = _mem[0].length;
        int m, n, x, y;
        int start, end;
        m = _index[idx][0];
        n = _index[idx][1];
        x = _index[idx][2];
        y = _index[idx][3];
        start = m * length + n;
        end = x * length + y;
        if (m == x) {
            return new String(_mem[m], n, end - start + 1);
        }

        byte[] result = new byte[end - start + 1];
        int relative_posi = 0;

        for (int i = m; i <= x; i++) {
            for (int j = 0; j < length; j++) {
                if (i == m && j == 0) j = n;
                if (i == x && j > y) {
                    break;
                }
                result[relative_posi] = _mem[i][j];
                relative_posi++;
            }
        }

        return new String(result, 0, relative_posi);

    }

    public int count() {
        return _index.length;
    }

    public String getname() {
        return _name;
    }
}
