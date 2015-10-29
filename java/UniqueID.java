/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Prateek
 */
public class UniqueID {
    static boolean isAlive[] = new boolean[42];
    
    public int getIdForGeneral(char color)
    {
        if(color=='R')
        {
            isAlive[0] = true;
            return 0;
        }
        else
        {
            isAlive[21] = true;
            return 21;
        }
    }
    
    public int getIdForColonel(char color)
    {
        if(color=='R')
        {
            for(int i=1;i<=2;i++)
            {
                if(!isAlive[i])
                {
                    isAlive[i]=true;
                    return i;
                }
            }
        }
        else
        {
            for(int i=22;i<=23;i++)
            {
                if(!isAlive[i])
                {
                    isAlive[i]=true;
                    return i;
                }
            }
        }
        return 0;
    }
    public int getIdForLeutenant(char color)
    {
        if(color=='R')
        {
            for(int i=3;i<=5;i++)
            {
                if(!isAlive[i])
                {
                    isAlive[i]=true;
                    return i;
                }
            }
        }
        else
        {
            for(int i=24;i<=26;i++)
            {
                if(!isAlive[i])
                {
                    isAlive[i]=true;
                    return i;
                }
            }
        }
        return 0;
    }public int getIdForMiner(char color)
    {
        if(color=='R')
        {
            for(int i=6;i<=9;i++)
            {
                if(!isAlive[i])
                {
                    isAlive[i]=true;
                    return i;
                }
            }
        }
        else
        {
            for(int i=27;i<=30;i++)
            {
                if(!isAlive[i])
                {
                    isAlive[i]=true;
                    return i;
                }
            }
        }
        return 0;
    }public int getIdForRider(char color)
    {
        if(color=='R')
        {
            for(int i=10;i<=14;i++)
            {
                if(!isAlive[i])
                {
                    isAlive[i]=true;
                    return i;
                }
            }
        }
        else
        {
            for(int i=31;i<=35;i++)
            {
                if(!isAlive[i])
                {
                    isAlive[i]=true;
                    return i;
                }
            }
        }
        return 0;
    }
    public int getIdForSpy(char color)
    {
        if(color=='R')
        {
            isAlive[15] = true;
            return 15;
        }
        else
        {
            isAlive[36] = true;
            return 36;
        }
        
    }
    public int getIdForBomb(char color)
    {
        if(color=='R')
        {
            for(int i=16;i<=19;i++)
            {
                if(!isAlive[i])
                {
                    isAlive[i]=true;
                    return i;
                }
            }
        }
        else
        {
            for(int i=37;i<=40;i++)
            {
                if(!isAlive[i])
                {
                    isAlive[i]=true;
                    return i;
                }
            }
        }
        return 0;
    }public int getIdForFlag(char color)
    {
        if(color=='R')
        {
            isAlive[20] = true;
            return 20;
        }
        else
        {
            isAlive[41] = true;
            return 41;
        }
        
    }
    
}
