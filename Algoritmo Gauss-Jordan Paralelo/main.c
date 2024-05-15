#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <omp.h>

void generarMatrizAleatoria(int n, float A[][n]) {
    srand(time(NULL));
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            A[i][j] = (float) rand() / RAND_MAX * 10; // números aleatorios entre 0 y 10
        }
    }
}

void inicializarMatrizIdentidad(int n, float I[][n]) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            I[i][j] = 0;
            if (i == j) {
                I[i][j] = 1;
            }
        }
    }
}

void gaussJordan(int n, float A[][n], float I[][n], int inicio, int fin) {
    float pivote, aux;
    short int i, j, k;
    for (i = inicio; i < fin; i++) {
        pivote = A[i][i];
        for (k = 0; k < n; k++) {
            A[i][k] = A[i][k] / pivote;
            I[i][k] = I[i][k] / pivote;
        }
        for (j = 0; j < n; j++) {
            if (i != j) {
                aux = A[j][i];
                for (k = 0; k < n; k++) {
                    A[j][k] = A[j][k] - aux * A[i][k];
                    I[j][k] = I[j][k] - aux * I[i][k];
                }
            }
        }
    }
}

void gaussJordan_par(int n, float A[][n], float I[][n], int bloques) {
    int tamano = n / bloques;
    omp_set_num_threads(bloques);
    #pragma omp parallel
    {
        int tarea = omp_get_thread_num();
        gaussJordan(n, A, I, tarea * tamano, (tarea + 1) * tamano);
    }
}

void imprimirMatrices(int n, float A[][n], float I[][n]) {
    short int i, j;
    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            printf("%f\t", A[i][j]);
        }
        printf("\t");
        for (j = 0; j < n; j++) {
            printf("%f\t", I[i][j]);
        }
        printf("\n");
    }
}

int main() {
    short int n = 4; //n es el numero de filas y columnas de la matriz nxn
    float A[n][n], I[n][n];
    generarMatrizAleatoria(n, A); // llamada a la función para generar números aleatorios
    inicializarMatrizIdentidad(n, I); // llamada a la función para inicializar la matriz identidad
    double inicio = omp_get_wtime();
    gaussJordan_par(n, A, I, 4); // llamada a la función que aplica el algoritmo de Gauss-Jordan de manera paralela con 4 hilos
    double duracion = (omp_get_wtime() - inicio);
    printf("Tiempo de ejecución del algoritmo de Gauss-Jordan paralelo: %f segundos\n", duracion);
    imprimirMatrices(n, A, I); // llamada a la función que imprime las matrices
    return 0;
}
