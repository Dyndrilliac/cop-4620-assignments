int i(int x, float y) { /* g */
        return 0;
}

float f(float x, int y) {
        return 0.1;
}

void main(void) {
        int x;
        int y[5];

        x = y[i(3+7,4.27)] - i(i(3,4.7), 4.5+f(38.92,51));
}