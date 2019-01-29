import javax.swing.text.*;

public class Mapgame {
	static Block[] block;
	public static void main(String[] args) {
		block= new Block[]{
			new Block(0,0,1,0,2,0,3,0,4,0),
			new Block(0,1,0,0,1,0,2,0,3,0),
			new Block(0,2,0,1,0,0,1,0,2,0),
			new Block(0,0,1,0,1,1,1,2,2,0),
			new Block(0,0,1,0,1,1,2,0,3,0),
			new Block(0,0,1,0,0,1,2,0,2,1),
			new Block(0,0,0,1,0,2,1,2,1,3),
			new Block(0,0,0,1,1,1,2,1,2,2),
			new Block(0,0,1,0,1,1,2,0,1,-1),
			new Block(0,0,1,0,1,1,2,1,2,2),
			new Block(0,0,1,0,1,1,2,0,2,1),
			new Block(0,0,0,1,1,1,1,2,2,1)
		};
		boolean[][] map= new boolean[12][5];//false代表空。
		TryAllPossible(0,map);
	}
	static boolean TryAllPossible(int i,boolean[][] map){
		//System.out.println("it's turn to "+i); 
		if(i==2){
			System.out.print("\n.\n"); 
		}else if(i==3){
			System.out.print("."); 
		}
		boolean[][] perMap=new boolean[12][5]; //臨時地圖
		boolean[][][] nextMap=new boolean[144][12][5]; //下一步的地圖集
		int index= 0; 
		boolean resultFound=false;
		for(int y=4;y>=0;y--){
			for(int x=11;x>=0;x--){
				for(int o=1;o<=4;o++){
					//循環240遍，嘗試所有位置。
					block[i].SetPosition(x,y,o); 
					//System.out.println(i+","+x+","+y+","+o);
					/*//輸出地圖開始
					for(int b=4;b>=0;b--){
						for(int a=11;a>=0;a--){
							if(!map[a][b]){
								System.out.print("O"); 
							}else{
								System.out.print("X"); 
							}
						}
						System.out.println("\n"); 
					}
					System.out.println("?\n");
					//輸出地圖結束*/
					if(block[i].FitInMap(map)){
						perMap= block[i].PutInMap(map); 
						/*//輸出地圖開始
						for(int b=4;b>=0;b--){
							for(int a=11;a>=0;a--){
								if(!perMap[a][b]){
									System.out.print("O"); 
								}else{
									System.out.print("X"); 
								}
							}
							System.out.println("\n"); 
						}
						System.out.println("\n\n\n");
						//輸出地圖結束*/
						if(i==11){ 
							resultFound=true; 
						}
						for(int b=4;b>=0;b--){
							for(int a=11;a>=0;a--){
								nextMap[index][a][b]=perMap[a][b]; 
								perMap[a][b]=map[a][b];
							}
						}
						index++; 
					}else{
						//System.out.println("cannot fit. ");
					}
					//循環節結束
				}
			}
		}
		if(i!=11){
			for(int i_=0;i_<index;i_++){
				for(int b=4;b>=0;b--){
					for(int a=11;a>=0;a--){
						perMap[a][b]= nextMap[i_][a][b];
					}
				}
				resultFound= TryAllPossible(i+1,perMap);
				for(int b=4;b>=0;b--){
					for(int a=11;a>=0;a--){
						if(resultFound){
							if(!perMap[a][b]){
								System.out.print("O"); 
							}else{
								System.out.print("X"); 
							}
						}
						perMap[a][b]=false;
					}
					if(resultFound){
						System.out.print("\n");
					}
				}
				if(resultFound){
					System.out.print("\n\n");
				}
			}
		}
		return resultFound; 
	}
	/*
	static void TryAllPossible(int i,boolean[][] map){
		System.out.println("it's turn to "+i); 
		for(int y=4;y>=0;y--){
			for(int x=11;x>=0;x--){
				for(int o=1;o<=4;o++){
					//循環240遍，嘗試所有位置。
					block[i].SetPosition(x,y,o); 
					//System.out.println(i+","+x+","+y+","+o);
					if(block[i].FitInMap(map)){ 
						//boolean[][] map=new boolean[12][5];
						map= block[i].PutInMap(map); 
						//輸出地圖開始
						for(int a=4;a>=0;a--){
							for(int b=11;b>=0;b--){
								if(map[b][a]){
									System.out.print("O"); 
								}else{
									System.out.print("X"); 
								}
							}
							System.out.println("\n"); 
						}
						System.out.println("\n\n\n");
						//輸出地圖結束
						if(i==11){ 
							System.out.println("one result found. "); 
							for(int j=0;j<=11;j++){
								System.out.println(block[j].MytoString());
							}
						}else{
							if(i<11){
								TryAllPossible(i+1,map);
								System.out.println(i); 
								//continue; 
							} 
						}
					}else{
						//continue;
					}
					//循環節結束
				}
			}
		}
	}
	*/
}

class Block{
	cordinate c1,c2,c3,c4,c5; //在地圖上的座標
	cordinate sc1,sc2,sc3,sc4,sc5; //形狀參數
	Block(int x1,int y1,int x2,int y2,int x3,int y3,int x4,int y4,int x5,int y5){
		c1=new cordinate();
		c2=new cordinate();
		c3=new cordinate();
		c4=new cordinate();
		c5=new cordinate();
		sc1=new cordinate(x1, y1);
		sc2=new cordinate(x2, y2);
		sc3=new cordinate(x3, y3);
		sc4=new cordinate(x4, y4);
		sc5=new cordinate(x5, y5);
	}
	void SetPosition(int x,int y,int Orient){
		//Orient為1、2、3或4，分別為正放與逆時針旋轉後的方向
		//position所指點即為形狀參數中的(0,0)點。
		cordinate position=new cordinate(x, y);
		c1.ResetCordinate(position,sc1, Orient);
		c2.ResetCordinate(position,sc2, Orient);
		c3.ResetCordinate(position,sc3, Orient);
		c4.ResetCordinate(position,sc4, Orient);
		c5.ResetCordinate(position,sc5, Orient);
	}
	boolean[][] PutInMap(boolean[][] map_){
		if(map_[c1.x][c1.y]|map_[c2.x][c2.y]|map_[c3.x][c3.y]|map_[c4.x][c4.y]|map_[c5.x][c5.y]){
			System.out.println("ERROR");
			System.exit(0);
		}
		//按順序填入積木
		boolean[][] map= new boolean[12][5];
		for(int b=4;b>=0;b--){
			for(int a=11;a>=0;a--){
				map[a][b]=map_[a][b];
			}
		}
		map[c1.x][c1.y]=true;
		map[c2.x][c2.y]=true;
		map[c3.x][c3.y]=true;
		map[c4.x][c4.y]=true;
		map[c5.x][c5.y]=true;
		return map; 
	}
	boolean FitInMap(boolean[][] map){//判斷是否能放進去
		if(c1.InTheMap()&c2.InTheMap()&c3.InTheMap()&c4.InTheMap()&c5.InTheMap()){
			return 
				!(map[c1.x][c1.y]|map[c2.x][c2.y]|map[c3.x][c3.y]|map[c4.x][c4.y]|map[c5.x][c5.y]); 
		}else{
			return false;
		}
	}
}

class cordinate {
	int x;
	int y;
	cordinate(){}
	cordinate(int X,int Y){
		x=X;
		y=Y;
	}
	void ResetCordinate(cordinate p,cordinate c,int m){
		//m即為積木的擺放方向
		if(m==1){
			x=p.x+c.x;
			y=p.y+c.y;
		}else if(m==2){
			x=p.x-c.y;
			y=p.y+c.x;
		}else if(m==3){
			x=p.x-c.x;
			y=p.y-c.y;
		}else if(m==4){
			x=p.x+c.y;
			y=p.y-c.x;
		}
	}
	boolean InTheMap(){//判斷這個點是否在範圍內
		if(x>=0&x<=11&y>=0&y<=4){
			return true; 
		}else{
			return false; 
		}
	}
}
