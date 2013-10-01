/**
 * In this case, 
 * R stands for Red, which is negative numbers in this context
 * G = Green, zeroes in this case
 * B = Blue, positive numbers in this case
 */
public class RGBSort {

	public static void sort(int[] arr) {
		int neg_end = 0;
		int len = arr.length;
		int pos_start = len - 1;
		int curr = neg_end;
		// int numZeroes = 0;
		while (curr < pos_start) {
			if (arr[curr] < 0) {
				swap(arr, curr, neg_end);
				neg_end++;
			} else if (arr[curr] > 0) {
				swap(arr, curr, pos_start);
				pos_start--;
			} else if (arr[curr] == 0) {
				curr++;
			}
			curr = Math.max(curr, neg_end);
		}
	}

	private static void swap(int[] arr, int a, int b) {
		int tmp = arr[a];
		arr[a] = arr[b];
		arr[b] = tmp;
	}

	public static void printArr(int[] arr) {
		for (int item : arr) {
			System.out.print(" " + item + " ");
		}
		System.out.println();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] arr = { -7, 2, -1, 0, 8, -8, 0, 0, 9, -5, 0 };
		printArr(arr);
		sort(arr);
		printArr(arr);
                // Sample output :  -7  -1  -5  -8  0  0  0  0  9  8  2 
	}
}
