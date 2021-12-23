import java.util.Arrays;
import java.util.Scanner;

public class BucketPrac {
		static int finalMinError = 999999;
		
		//오차 구하는 함수
		public static int calError( int[] realItems, int start, int end, int for_div) { //for_div는 그룹의 원소 개수
			
			//bucketIndex로 막대기 위치가 넘어옴
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
			
			average = total/for_div;//bucketIndex 0일때 원소  1개 1일때 2개니까
			System.out.println("average:" + average); 
			
			if(total % for_div != 0) //소수점 있는 경우 up평균구하기
				average_ifNotInt = average + 1;		


			for(int h = start; h <= end; h++) 
				err += (realItems[h]-average) * (realItems[h]-average);//제곱구하기
			System.out.println("err:" + err);
			
			for(int h = start; h <= end; h++) 
				err_ifNotInt +=  (realItems[h]-average_ifNotInt) * (realItems[h]-average_ifNotInt);//제곱구하기
			System.out.println("err:" + err_ifNotInt);
			
			
			err = Math.min(err, err_ifNotInt); //둘 중 작은게 err가 됨
			System.out.println("err:" + err); 
			
			return err;

							
		}
		
		
		//버킷 속 막대기 가지고 구한 최종 오차값을 구하는 함수
		public static int Error(int[] divResult, int divResultSum, int[] realItems, int[] bucket) { //뽑힌 애들이 넘어옴
			//만약 <5,11>이 뽑혔으면
			//int[] arr = 0~5까지 // 6~11까지 //12부터 ~ 끝까지(realItems.length-1)
			//calError( int[] realItems, int start, int end, int for_div)
			for (int i = 0; i < bucket.length; i++)
				System.out.print(bucket[i] + " "); 
			System.out.println();
			
			int i = 0;
			
			for(int j = 0; j < bucket.length; j++) { //버킷에 뽑힌 막대기들 #2개
				//첫 공일 경우
				if(j == 0) {
					int start = 0;//처음부터
					int end = bucket[j]; //현재 공까지
					System.out.println("start: " + start + " end: " + end); 
					divResult[i] = calError(realItems, start, end, end - start + 1);
					System.out.println("i: "+ i + " divResult: " + divResult[i]); 
					i++;
				}
				
				//끝 공일 경우 1)끝공까지인 그룹 2) 끝공 다음부터 arr끝까지의 그룹
				else if(j == bucket.length-1) {
					//1)끝공까지인 그룹 
					int start = bucket[j-1] + 1;//이전 공  다음 자리부터
					int end = bucket[j]; //현재 공까지
					System.out.println("start: " + start + " end: " + end); 
					divResult[i] = calError(realItems, start, end, end - start + 1);
					System.out.println("i: "+ i + " divResult: " + divResult[i]); 
					i++;
					
					//2) 끝공 다음부터 arr끝까지의 그룹
					start = bucket[j] + 1;//이전 공  다음 자리부터
					end = realItems.length-1; //끝까지
					System.out.println("start: " + start + " end: " + end); 
					divResult[i] = calError(realItems, start, end, end - start + 1);
					System.out.println("i: "+ i + " divResult: " + divResult[i]); 
					//마지막 그룹이니까 i증가 필요 없음
				}
				
				//중간 공일 경우
				else {
					int start = bucket[j-1] + 1;//이전 공  다음 자리부터
					int end = bucket[j]; //현재 공까지
					System.out.print("이건 안 돌 거 start: " + start + " end: " + end); 
					divResult[i] = calError(realItems, start, end, end - start + 1);
					System.out.println("이건 안 돌 거 i: "+ i + " divResult: " + divResult[i]); 
					i++;
				}
			}
			
			//for문을 나가면 divResult가 채워있을 것.
			int calErrorinErrorMethod = 0;
			
			for(int j = 0; j < divResult.length; j++) {
				calErrorinErrorMethod += divResult[j];
			}
			
//			if(calErrorinErrorMethod < divResultSum) //이번 루트에서 구한 오차가 더 작으면 그걸로 바꿈
//				divResultSum = calErrorinErrorMethod;
//			System.out.println("divResultSum: "+ divResultSum); 
			return calErrorinErrorMethod;
		}
		
		//조합코드 	solution(divResult,inputData,forSepBucketData,sepBucket,groupSize-1);
		public static void solution(int[] divResult, int divResultSum, int[] realItems, int[] bucketItems, int[] bucket, int k) { //조합 - k개 뽑을 거다
			
			if( k == 0 ) { 
//				for (int i = 0; i < bucket.length; i++)
//					System.out.print(bucket[i] + " "); 
//				System.out.println();
				
				
				divResultSum = Error(divResult, divResultSum, realItems,bucket); //뽑힌 공이 bucket에 들어가 있는 상황
				System.out.println("divResultSum: "+ divResultSum); 
				
				if(divResultSum<finalMinError)
					finalMinError = divResultSum;
				
				return;
			}
			
			int lastIndex = bucket.length - k - 1; // 가장 최근에 뽑힌 수가 저장된 위치 index
			
			for(int i = 0; i < bucketItems.length; i++){
				
				if (bucket.length == k){  // 아직 한 번도 뽑은 적이 없는 경우
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

			//문제 조건: 들어오는 input은 모두 int형이다.
			Scanner sc = new Scanner(System.in);
			int inputSize = sc.nextInt(); //숫자를 총 몇 개 받을 건지
			int groupSize = sc.nextInt(); //총 몇 개의 그룹으로 나눌 건지
			int[] divResult =  new int[groupSize];//나눠지는 그룹은 bucket막대기+1만큼 생김
			
			//long beforeTime = System.currentTimeMillis();
			//Data를 입력받는다
			int[] inputData =  new int[inputSize]; 
			for(int i = 0; i < inputData.length ; i++)
				inputData[i] = sc.nextInt();
			
//			//-----test --0K
//			for(int i = 0; i < inputData.length ; i++)
//				System.out.print(inputData[i] + " "); 
//			System.out.println();
//			//----------
			
			//막대기 뽑을 때 맨 뒤에 애는 뽑으면 안되니까 맨 마지막 data 빼고 막대기로 만들 수 있는 애들의 인덱스로만 배열 다시 만듦			
			int[] forSepBucketData =  new int[inputSize-1]; 
			for(int i = 0; i < forSepBucketData.length ; i++)
				forSepBucketData[i] = i;
			
//			//-----test--0K
//			for(int i = 0; i < forSepBucketData.length ; i++)
//				System.out.print(forSepBucketData[i] + " "); 
//			System.out.println();
//			//----------
//			
			//힌트: 입력 받은 Data를 정렬한다. => 정렬해야 분류하기 쉬움
			Arrays.sort(inputData);
			
//			//-----test--0K
//			for(int i = 0; i < inputData.length ; i++)
//				System.out.print(inputData[i] + " "); 
//			System.out.println();
//			//----------
//			System.out.println("divResult.length:" + divResult.length); 
			
			
			//그룹을 나누는 막대기를 꼽을 수 있는 곳은 0 ~ inputData - 2개이다. 
			//이 중에서 buketSize-1개를 뽑으면 buketSize만큼 그룹화 할 수 있다.
			
			//그룹 나눌 막대기 뽑는 버킷(조합) - groupSize-1개 뽑아야함
			//========예제로 보았을 때 3이니까 2
			int[] sepBucket = new int[groupSize-1]; 
			int divResultSum = 999999; //모든 그룹의 오차를 더한 것
			
			//forSepBucketData 막대기로 만들 수 있는 data들 중 막대기 개수만큼 뽑는다.
			solution(divResult, divResultSum, inputData,forSepBucketData,sepBucket,groupSize-1);
			
			System.out.print(finalMinError); 
//			long afterTime = System.currentTimeMillis(); 
//			long secDiffTime = (afterTime - beforeTime)/100;
//			System.out.println("시간차이(m) : "+secDiffTime);

			sc.close();

		}
}
