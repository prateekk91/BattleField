public enum Rank
{
	WATER,
	ONE,
	TWO,
	THREE,
	FOUR,
	FIVE,
	SPY,
	BOMB,
	FLAG,
	UNKNOWN,
	NIL;

	public int toInt()
	{
		switch (this)
		{
		case ONE:
			return 1;
		case TWO:
			return 2;
		case THREE:
			return 3;
		case FOUR:
			return 4;
		case FIVE:
			return 5;
                    case SPY:
                        return 6;
		default:
			return 0;
		}
	}
	
	
}