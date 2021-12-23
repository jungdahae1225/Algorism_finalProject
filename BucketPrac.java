import java.util.Arrays;
import java.util.Scanner;

public class BucketPrac {
		static int finalMinError = 999999;
		
		//���� ���ϴ� �Լ�
		public static int calError( int[] realItems, int start, int end, int for_div) { //for_div�� �׷��� ���� ����
			
			//bucketIndex�� ����� ��ġ�� �Ѿ��
			int total = 0;
			int average = 0;
			int average_ifNotInt = 0;
			int err = 0;
			int err_ifNotInt = 0;
			
//			//-----test --
//			for(int i = 0; i < realItems.length ; i++)
//				System.out.print(realItems[i] + " "); 
//			System.out.println();
//			//----------
			
			//System.out.print("start: " + start + " end: " + end); 
			//System.out.println("for_div: " + for_div); 
			for(int h = start; h <= end; h++)
				total += realItems[h];
			
			System.out.println("total:" + total);
			
			average = total/for_div;//bucketIndex 0�϶� ����  1�� 1�϶� 2���ϱ�
			System.out.println("average:" + average); 
			
			if(total % for_div != 0) //�Ҽ��� �ִ� ��� up��ձ��ϱ�
				average_ifNotInt = average + 1;		


			for(int h = start; h <= end; h++) 
				err += (realItems[h]-average) * (realItems[h]-average);//�������ϱ�
			System.out.println("err:" + err);
			
			for(int h = start; h <= end; h++) 
				err_ifNotInt +=  (realItems[h]-average_ifNotInt) * (realItems[h]-average_ifNotInt);//�������ϱ�
			System.out.println("err:" + err_ifNotInt);
			
			
			err = Math.min(err, err_ifNotInt); //�� �� ������ err�� ��
			System.out.println("err:" + err); 
			
			return err;

							
		}
		
		
		//��Ŷ �� ����� ������ ���� ���� �������� ���ϴ� �Լ�
		public static int Error(int[] divResult, int divResultSum, int[] realItems, int[] bucket) { //���� �ֵ��� �Ѿ��
			//���� <5,11>�� ��������
			//int[] arr = 0~5���� // 6~11���� //12���� ~ ������(realItems.length-1)
			//calError( int[] realItems, int start, int end, int for_div)
			for (int i = 0; i < bucket.length; i++)
				System.out.print(bucket[i] + " "); 
			System.out.println();
			
			int i = 0;
			
			for(int j = 0; j < bucket.length; j++) { //��Ŷ�� ���� ������ #2��
				//ù ���� ���
				if(j == 0) {
					int start = 0;//ó������
					int end = bucket[j]; //���� ������
					System.out.println("start: " + start + " end: " + end); 
					divResult[i] = calError(realItems, start, end, end - start + 1);
					System.out.println("i: "+ i + " divResult: " + divResult[i]); 
					i++;
				}
				
				//�� ���� ��� 1)���������� �׷� 2) ���� �������� arr�������� �׷�
				else if(j == bucket.length-1) {
					//1)���������� �׷� 
					int start = bucket[j-1] + 1;//���� ��  ���� �ڸ�����
					int end = bucket[j]; //���� ������
					System.out.println("start: " + start + " end: " + end); 
					divResult[i] = calError(realItems, start, end, end - start + 1);
					System.out.println("i: "+ i + " divResult: " + divResult[i]); 
					i++;
					
					//2) ���� �������� arr�������� �׷�
					start = bucket[j] + 1;//���� ��  ���� �ڸ�����
					end = realItems.length-1; //������
					System.out.println("start: " + start + " end: " + end); 
					divResult[i] = calError(realItems, start, end, end - start + 1);
					System.out.println("i: "+ i + " divResult: " + divResult[i]); 
					//������ �׷��̴ϱ� i���� �ʿ� ����
				}
				
				//�߰� ���� ���
				else {
					int start = bucket[j-1] + 1;//���� ��  ���� �ڸ�����
					int end = bucket[j]; //���� ������
					System.out.print("�̰� �� �� �� start: " + start + " end: " + end); 
					divResult[i] = calError(realItems, start, end, end - start + 1);
					System.out.println("�̰� �� �� �� i: "+ i + " divResult: " + divResult[i]); 
					i++;
				}
			}
			
			//for���� ������ divResult�� ä������ ��.
			int calErrorinErrorMethod = 0;
			
			for(int j = 0; j < divResult.length; j++) {
				calErrorinErrorMethod += divResult[j];
			}
			
//			if(calErrorinErrorMethod < divResultSum) //�̹� ��Ʈ���� ���� ������ �� ������ �װɷ� �ٲ�
//				divResultSum = calErrorinErrorMethod;
//			System.out.println("divResultSum: "+ divResultSum); 
			return calErrorinErrorMethod;
		}
		
		//�����ڵ� 	solution(divResult,inputData,forSepBucketData,sepBucket,groupSize-1);
		public static void solution(int[] divResult, int divResultSum, int[] realItems, int[] bucketItems, int[] bucket, int k) { //���� - k�� ���� �Ŵ�
			
			if( k == 0 ) { 
//				for (int i = 0; i < bucket.length; i++)
//					System.out.print(bucket[i] + " "); 
//				System.out.println();
				
				
				divResultSum = Error(divResult, divResultSum, realItems,bucket); //���� ���� bucket�� �� �ִ� ��Ȳ
				System.out.println("divResultSum: "+ divResultSum); 
				
				if(divResultSum<finalMinError)
					finalMinError = divResultSum;
				
				return;
			}
			
			int lastIndex = bucket.length - k - 1; // ���� �ֱٿ� ���� ���� ����� ��ġ index
			
			for(int i = 0; i < bucketItems.length; i++){
				
				if (bucket.length == k){  // ���� �� ���� ���� ���� ���� ���
					bucket[0] = bucketItems[i];
					solution(divResult, divResultSum, realItems, bucketItems, bucket, k - 1);
				}
				
				else if ( bucket[lastIndex] < bucketItems[i] ) {
					bucket[lastIndex+1] = bucketItems[i];
					solution(divResult, divResultSum, realItems, bucketItems, bucket, k - 1);
				}
			}
		}
		
		public static void main(String[] args) {
			// TODO Auto-generated method stub

			//���� ����: ������ input�� ��� int���̴�.
			Scanner sc = new Scanner(System.in);
			int inputSize = sc.nextInt(); //���ڸ� �� �� �� ���� ����
			int groupSize = sc.nextInt(); //�� �� ���� �׷����� ���� ����
			int[] divResult =  new int[groupSize];//�������� �׷��� bucket�����+1��ŭ ����
			
			//long beforeTime = System.currentTimeMillis();
			//Data�� �Է¹޴´�
			int[] inputData =  new int[inputSize]; 
			for(int i = 0; i < inputData.length ; i++)
				inputData[i] = sc.nextInt();
			
//			//-----test --0K
//			for(int i = 0; i < inputData.length ; i++)
//				System.out.print(inputData[i] + " "); 
//			System.out.println();
//			//----------
			
			//����� ���� �� �� �ڿ� �ִ� ������ �ȵǴϱ� �� ������ data ���� ������ ���� �� �ִ� �ֵ��� �ε����θ� �迭 �ٽ� ����			
			int[] forSepBucketData =  new int[inputSize-1]; 
			for(int i = 0; i < forSepBucketData.length ; i++)
				forSepBucketData[i] = i;
			
//			//-----test--0K
//			for(int i = 0; i < forSepBucketData.length ; i++)
//				System.out.print(forSepBucketData[i] + " "); 
//			System.out.println();
//			//----------
//			
			//��Ʈ: �Է� ���� Data�� �����Ѵ�. => �����ؾ� �з��ϱ� ����
			Arrays.sort(inputData);
			
//			//-----test--0K
//			for(int i = 0; i < inputData.length ; i++)
//				System.out.print(inputData[i] + " "); 
//			System.out.println();
//			//----------
//			System.out.println("divResult.length:" + divResult.length); 
			
			
			//�׷��� ������ ����⸦ ���� �� �ִ� ���� 0 ~ inputData - 2���̴�. 
			//�� �߿��� buketSize-1���� ������ buketSize��ŭ �׷�ȭ �� �� �ִ�.
			
			//�׷� ���� ����� �̴� ��Ŷ(����) - groupSize-1�� �̾ƾ���
			//========������ ������ �� 3�̴ϱ� 2
			int[] sepBucket = new int[groupSize-1]; 
			int divResultSum = 999999; //��� �׷��� ������ ���� ��
			
			//forSepBucketData ������ ���� �� �ִ� data�� �� ����� ������ŭ �̴´�.
			solution(divResult, divResultSum, inputData,forSepBucketData,sepBucket,groupSize-1);
			
			System.out.print(finalMinError); 
//			long afterTime = System.currentTimeMillis(); 
//			long secDiffTime = (afterTime - beforeTime)/100;
//			System.out.println("�ð�����(m) : "+secDiffTime);

			sc.close();

		}
}
