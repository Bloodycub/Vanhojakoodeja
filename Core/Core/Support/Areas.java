package Core.Support;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;

public class Areas {

	public static final Area PortBank = new Area(3043, 3237, 3048, 3234);
	public static final Area FaladorWallcuttingspot = new Area(3068, 3327, 3046, 3313);
	public static final Area PortOakspot = new Area(2980, 3208, 2985, 3203);
	public static final Area PortOakspotBig = new Area(2976, 3210, 2992, 3196);
	public static final Area PortWillowspot = new Area(3064, 3255, 3056, 3251);
	public static final Area RimmingtonYewSpot = new Area(2941, 3235, 2934, 3230);
	public static final Area VarrocGrandextange = new Area(3161, 3487, 3168, 3486);
	public static final Area LumbgeSheepPit = new Area(3194, 3273, 3209, 3259);
	public static final Area LumbgechickenHut = new Area(3192, 3275, 3184, 3270);
	public static final Area LumbgeSheepareabig = new Area(3193, 3278, 3212, 3257);
	public static final Area LumbgeCastleWhoolSpinning = new Area(3208, 3215, 3212, 3212).setPlane(1);
	public static final Area LumbgeSpawn = new Area(3227, 3235, 3233, 3230);
	public static final Area LBChest = new Area(3222, 3218, 3221, 3216); // Lumbgre
	public static final Area LBSpawnarea = new Area(3222, 3236, 3240, 3219); // Spawn area
	public static final Area Cookroom = new Area(3205, 3217, 3212, 3212);
	public static final Area Seller = new Area(3216, 9624, 3215, 9624);
	public static final Area SellerS = new Area(3210, 9615, 3208, 9618);
	public static final Area SellerFULL = new Area(3219, 9626, 3207, 9613);
	public static final Area Eggs = new Area(3176, 3305, 3174, 3306);
	public static final Area Wheat = new Area(new int[][] { { 3161, 3291 }, { 3162, 3291 }, { 3163, 3292 },
			{ 3163, 3295 }, { 3163, 3296 }, { 3164, 3297 }, { 3164, 3298 }, { 3164, 3299 }, { 3162, 3302 },
			{ 3161, 3303 }, { 3160, 3305 }, { 3159, 3306 }, { 3157, 3307 }, { 3154, 3307 }, { 3153, 3306 },
			{ 3153, 3297 }, { 3155, 3295 }, { 3157, 3295 }, { 3161, 3291 } });
	public static final Area Mill = new Area(3165, 3306, 3164, 3306);
	public static final Area Cow = new Area(3173, 3318, 3173, 3318);
	public static final Area LB = new Area(3268, 3190, 3132, 3317); // Lumbgre
	public static final Area Pickarea = new Area(3146, 3325, 3198, 3275);
	public static final Area Chickenpit = new Area(3014, 3298, 3020, 3282);
	public final static Area ChickenpitLb = new Area(new int[][] { { 3173, 3308 }, { 3180, 3308 }, { 3180, 3304 },
			{ 3182, 3304 }, { 3183, 3303 }, { 3184, 3303 }, { 3186, 3301 }, { 3186, 3299 }, { 3187, 3298 },
			{ 3187, 3296 }, { 3186, 3295 }, { 3186, 3291 }, { 3184, 3289 }, { 3178, 3289 }, { 3177, 3288 },
			{ 3175, 3288 }, { 3174, 3289 }, { 3171, 3289 }, { 3169, 3291 }, { 3169, 3295 }, { 3170, 3296 },
			{ 3170, 3298 }, { 3169, 3299 }, { 3169, 3300 }, { 3173, 3304 }, { 3173, 3307 } });
	public static final Area ShrimpfishingBIGarea = new Area(
			new int[][] { { 2984, 3176 }, { 2993, 3161 }, { 2995, 3154 }, { 2991, 3145 }, { 2992, 3141 },
					{ 2997, 3140 }, { 2996, 3148 }, { 3001, 3156 }, { 3000, 3164 }, { 2987, 3183 }, { 2981, 3181 } });
	public final static Area SwordUpgrade = new Area(3233, 3235, 3228, 3233);
	public final static Area Chickenpitaxespot = new Area(3231, 3297, 3234, 3295);
	public final static Area Sheepsafespot = new Area(3226, 3234, 3222, 3236);
	public final Area Shrimpspot = new Area(2998, 3159, 2999, 3156);
	public final static Area AnovyBigarea = new Area(3078, 3239, 3093, 3220);
	public final static Area Anovyspot = new Area(3087, 3229, 3089, 3230);
	public final static Area PortShop = new Area(
			new int[][] { { 3012, 3208 }, { 3017, 3208 }, { 3017, 3203 }, { 3012, 3203 } });
	public final static Area LbCookingStove = new Area(3211, 3215, 3210, 3213);
	public final static Area CopperoreQuarry = new Area(2981, 3249, 2975, 3244);
	public final static Area IronoreQuarry = new Area(2973, 3236, 2967, 3242);
	public final static Area ClayQuarry = new Area(2989, 3241, 2985, 3239);
	public final static Area Doricsquesthut = new Area(2952, 3450, 2951, 3451);
	public static Area CowPitInfalador = new Area(new int[][] { { 2928, 3291 }, { 2928, 3286 }, { 2930, 3285 },
			{ 2930, 3281 }, { 2927, 3278 }, { 2927, 3276 }, { 2929, 3274 }, { 2933, 3279 }, { 2937, 3279 },
			{ 2939, 3276 }, { 2939, 3273 }, { 2937, 3271 }, { 2936, 3269 }, { 2935, 3269 }, { 2933, 3267 },
			{ 2928, 3267 }, { 2926, 3269 }, { 2924, 3269 }, { 2922, 3271 }, { 2922, 3274 }, { 2912, 3290 },
			{ 2913, 3291 }, { 2914, 3291 }, { 2916, 3293 }, { 2921, 3293 }, { 2922, 3292 }, { 2928, 3292 } });
	public final static Area LbCoookroomstovebig = new Area(3212, 3217, 3209, 3212);
	public final static Area Xmarkquestnpc = new Area(3226, 3242, 3230, 3239);
	public final static Area FaladorWestbankWaterspot = new Area(2948, 3384, 2950, 3382);
	public final static Area Sheartable = new Area(3191, 3271, 3190, 3273);
	public final static Area WaterspotLbSeller = new Area(3212, 9624, 3212, 9624);
	public final static Area SheepshrederHut = new Area(3184, 3275, 3192, 3270);
	public final static Area Upperflour = new Area(3164, 3306, 3165, 3305).setPlane(2);
	public final static Area UpperflourBig = new Area(3161, 3310, 3172, 3302).setPlane(2);
	public final static Area Wheatbig = new Area(3152, 3308, 3165, 3289);
	///////////////////////// BANKS///////////////////////////////////////	
	public final static Area LumbgeBank = new Area(3210, 3218, 3208, 3220).setPlane(2);
	public final static Area DraynorBank = new Area(3094, 3245, 3092, 3241);
	public final static Area VarrokAlapankki = new Area(3183, 3437, 3181, 3443);
	public final static Area VarrockBankupperbank = new Area(3254, 3422, 3252, 3419);
	public final static Area FaladorWestBank = new Area(new int[][] { { 2948, 3367 }, { 2945, 3367 }, { 2945, 3368 },
			{ 2943, 3368 }, { 2943, 3370 }, { 2948, 3370 } });
	public final static Area Ge = new Area(
			new int[][] { { 3164, 3488 }, { 3166, 3488 }, { 3167, 3489 }, { 3167, 3491 }, { 3166, 3492 },
					{ 3163, 3492 }, { 3163, 3489 }, { 3161, 3487 }, { 3167, 3487 }, { 3169, 3488 }, { 3169, 3491 },
					{ 3168, 3493 }, { 3164, 3494 }, { 3160, 3493 }, { 3159, 3491 }, { 3161, 3487 }, { 3163, 3489 } });
	/////////////////////////////////////////////////////////////////////
	public final static Area VarrockMid = new Area(3208, 3433, 3217, 3424);
	public final static Area JuletBalcony = new Area(3160, 3425, 3157, 3426).setPlane(1);;
	public final static Area Varrockpriest = new Area(3253, 3482, 3252, 3485);
	public final static Area Cadavaberrys = new Area(3271, 3371, 3265, 3365);
	public final static Area Potionshop = new Area(3198, 3406, 3192, 3402);
	public final static Area JuletBalconybig = new Area(3153, 3428, 3163, 3423).setPlane(1);
	public final static Area DukeHoracioRoom = new Area(3208, 3225, 3212, 3218).setPlane(1);
	public final static Area SedriodorRoom = new Area(3106, 9566, 3102, 9574);
	public final static Area VarroWizzard = new Area(
			new int[][] { { 3253, 3403 }, { 3255, 3402 }, { 3254, 3400 }, { 3251, 3400 }, { 3251, 3402 } });
	public final static Area PotatofieldBig = new Area(3138, 3292, 3158, 3265);
	public final static Area Potatofield = new Area(3140, 3290, 3157, 3277);
	public final static Area EssencemineBIG = new Area(2879, 4865, 2943, 4802);

	public final static Area EssenceMineSpot1 = new Area(9400, 3931, 9411, 3942);
	public final static Area EssenceMineSpot2 = new Area(9398, 3974, 9413, 3962);
	public final static Area EssenceMineSpot3 = new Area(9379, 3979, 9371, 3960);
	public final static Area EssenceMineSpot4 = new Area(9369, 3930, 9382, 3943);

	public final static Area EssenceMineSpot4mine = new Area(2925, 4819, 2922, 4817);
	public final static Area EssenceMineSpot3mine = new Area(2900, 4820, 2896, 4817);
	public final static Area EssenceMineSpot2mine = new Area(2898, 4847, 2894, 4844);
	public final static Area EssenceMineSpot1mine = new Area(2925, 4844, 2922, 4847);
	public final static Area EssenceMineSpotMID = new Area(9393, 3959, 9384, 3943);

	public final static Area EssenceMineSpotteleport1 = new Area(9406, 3936, 9407, 3939);
	public final static Area EssenceMineSpotteleport2 = new Area(9404, 3968, 9406, 3965);
	public final static Area EssenceMineSpotteleport3 = new Area(9379, 3965, 9374, 3966);
	public final static Area EssenceMineSpotteleport4 = new Area(9374, 3937, 9378, 3938);
	public final static Area RuneEssenceminepath = new Area(3247, 3428, 3263, 3396);
	public final static Area PortMageShop = new Area(3012, 3260, 3015, 3257);
	public final static Area Wichersquest = new Area(2969, 3207, 2966, 3204);
	public final static Area RatTail = new Area(2954, 3204, 2959, 3203);
	public final static Area Questcowpitforburnmeat = new Area(3037, 3299, 3030, 3306);
	public final static Area Cookpitformeat = new Area(3033, 3296, 3037, 3295);
	public final static Area Onionfield = new Area(2955, 3253, 2946, 3248);
	public final static Area WichersquestBig = new Area(2964, 3209, 2971, 3202);
	public final static Area Quarrybig = new Area(2966, 3252, 2990, 3227);
	public final static Area Diplomacygoblinhut = new Area(2956, 3513, 2961, 3510);
	public final static Area box1 = new Area(2951, 3508, 2952, 3507);
	public final static Area box2 = new Area(2959, 3514, 2961, 3515);
	public final static Area box3 = new Area(2953, 3498, 2956, 3496).setPlane(2);
	public final static Area BlueDyeGuy = new Area(3029, 3380, 3027, 3378);
	public final static Area Redberrys = new Area(
			new int[][] { { 3271, 3376 }, { 3280, 3376 }, { 3279, 3369 }, { 3273, 3372 } });
	public final static Area Berryone = new Area(3271, 3375, 3274, 3374);
	public final static Area LBOnionField = new Area(3188, 3269, 3192, 3265);
	public final static Area Varrockwoodcutting = new Area(new int[][] { { 3163, 3465 }, { 3145, 3465 }, { 3145, 3460 },
			{ 3144, 3459 }, { 3144, 3454 }, { 3146, 3454 }, { 3149, 3452 }, { 3149, 3442 }, { 3156, 3442 },
			{ 3160, 3447 }, { 3163, 3449 }, { 3175, 3449 }, { 3173, 3456 }, { 3167, 3460 } });
	public final static Area VarrockmidBig = new Area(new int[][] { { 3205, 3431 }, { 3205, 3437 }, { 3212, 3438 },
			{ 3219, 3437 }, { 3223, 3430 }, { 3222, 3423 }, { 3220, 3421 }, { 3214, 3421 }, { 3212, 3419 },
			{ 3208, 3420 }, { 3206, 3423 }, { 3205, 3427 } });
	public final static Area SwordTrainer = new Area(3216, 3240, 3220, 3236);
	public final static Area Tutisland2 = new Area(1945, 6421, 1409, 5785);
	public final static Area Tutisland = new Area(
		    new int[][]{
		        { 3141, 3074 },
		        { 3133, 3078 },
		        { 3112, 3089 },
		        { 3095, 3085 },
		        { 3085, 3081 },
		        { 3066, 3079 },
		        { 3062, 3094 },
		        { 3065, 3112 },
		        { 3070, 3134 },
		        { 3091, 3136 },
		        { 3101, 3115 },
		        { 3112, 3107 },
		        { 3109, 3130 },
		        { 3128, 3132 },
		        { 3136, 3126 },
		        { 3136, 3103 },
		        { 3145, 3097 },
		        { 3146, 3076 }
		    }
		);
	public final static Area GoblinArea = new Area(3008, 3197, 2993, 3215);
	
	
	public static Position GoblinArea() {
		return GoblinArea.getCentralPosition();
	}
	public static Position ChickenpitLb() {
		return ChickenpitLb.getRandomPosition();
	}
	public static Position SwordTrainer() {
		return SwordTrainer.getRandomPosition();
	}
	public static Position Varrockwoodcutting() {
		return Varrockwoodcutting.getRandomPosition();
	}

	public static Position LBOnionField() {
		return LBOnionField.getRandomPosition();
	}

	public static Position Berryone() {
		return Berryone.getRandomPosition();
	}

	public static Position Redberrys() {
		return Redberrys.getRandomPosition();
	}

	public static Position BlueDyeGuy() {
		return BlueDyeGuy.getRandomPosition();
	}

	public static Position FaladorWestBank() {
		return FaladorWestBank.getRandomPosition();
	}

	public static Position box1() {
		return box1.getRandomPosition();
	}

	public static Position box2() {
		return box2.getRandomPosition();
	}

	public static Position box3() {
		return box3.getRandomPosition();
	}

	public static Position Diplomacygoblinhut() {
		return Diplomacygoblinhut.getRandomPosition();
	}

	public static Position PortOakspot() {
		return PortOakspot.getRandomPosition();
	}

	public static Position IronoreQuarry() {
		return IronoreQuarry.getRandomPosition();
	}

	public static Position CopperoreQuarry() {
		return CopperoreQuarry.getRandomPosition();
	}

	public static Position ClayQuarry() {
		return ClayQuarry.getRandomPosition();
	}

	public static Position Onionfield() {
		return Onionfield.getRandomPosition();
	}

	public static Position Cookpitformeat() {
		return Cookpitformeat.getRandomPosition();
	}

	public static Position Questcowpitforburnmeat() {
		return Questcowpitforburnmeat.getRandomPosition();
	}

	public static Position RatTail() {
		return RatTail.getRandomPosition();
	}

	public static Position Wichersquest() {
		return Wichersquest.getRandomPosition();
	}

	public static Position PortMageShop() {
		return PortMageShop.getRandomPosition();
	}

	public static Position EssenceMineSpotteleport1() {
		return EssenceMineSpotteleport1.getRandomPosition();
	}

	public static Position EssenceMineSpotteleport2() {
		return EssenceMineSpotteleport2.getRandomPosition();
	}

	public static Position EssenceMineSpotteleport3() {
		return EssenceMineSpotteleport3.getRandomPosition();
	}

	public static Position EssenceMineSpotteleport4() {
		return EssenceMineSpotteleport4.getRandomPosition();
	}

	public static Position EssenceMineSpot3mine() {
		return EssenceMineSpot3mine.getRandomPosition();
	}

	public static Position EssenceMineSpot2mine() {
		return EssenceMineSpot2mine.getRandomPosition();
	}

	public static Position EssenceMineSpot1mine() {
		return EssenceMineSpot1mine.getRandomPosition();
	}

	public static Position VarrockBankupperbank() {
		return VarrockBankupperbank.getRandomPosition();
	}

	public static Position Potatofield() {
		return Potatofield.getRandomPosition();
	}

	public static Position VarroWizzard() {
		return VarroWizzard.getRandomPosition();
	}

	public static Position SedriodorRoom() {
		return SedriodorRoom.getRandomPosition();
	}

	public static Position DukeHoracioRoom() {
		return DukeHoracioRoom.getRandomPosition();
	}

	public static Position Potionshop() {
		return Potionshop.getRandomPosition();
	}

	public static Position Cadavaberrys() {
		return Cadavaberrys.getRandomPosition();
	}

	public static Position Varrockpriest() {
		return Varrockpriest.getRandomPosition();
	}

	public static Position JuletBalcony() {
		return JuletBalcony.getRandomPosition();
	}

	public static Position VarrockMid() {
		return VarrockMid.getRandomPosition();
	}

	public static Position VarrokAlapankki() {
		return VarrokAlapankki.getRandomPosition();
	}

	public static Position DraynorBank() {
		return DraynorBank.getRandomPosition();
	}

	public static Position LumbgeBank() {
		return LumbgeBank.getRandomPosition();
	}

	public static Position Upperflour() {
		return Upperflour.getRandomPosition();
	}

	public static Position WaterspotLbSeller() {
		return WaterspotLbSeller.getRandomPosition();
	}

	public static Position FaladorWestbankWaterspot() {
		return FaladorWestbankWaterspot.getRandomPosition();
	}

	public static Position Sheartable() {
		return Sheartable.getRandomPosition();
	}

	public static Position Xmarkquestnpc() {
		return Xmarkquestnpc.getRandomPosition();
	}

	public static Position CowPitInfalador() {
		return CowPitInfalador.getRandomPosition();
	}

	public static Position Doricsquesthut() {
		return Doricsquesthut.getRandomPosition();
	}

	public static Position LbCookingStove() {
		return LbCookingStove.getRandomPosition();
	}

	public Position PortShop() {
		return PortShop.getRandomPosition();
	}

	public Position Chickenpit() {
		return Chickenpit.getRandomPosition();
	}

	public Position AnovyBigarea() {
		return AnovyBigarea.getRandomPosition();
	}

	public Position Shrimpspot() {
		return Shrimpspot.getRandomPosition();
	}

	public Position Anovyspot() {
		return Anovyspot.getRandomPosition();
	}

	public Position SwordUpgrade() {
		return SwordUpgrade.getRandomPosition();
	}

	public Position Mill() {
		return Mill.getRandomPosition();
	}

	public Position Cow() {
		return Cow.getRandomPosition();
	}

	public Position Pickarea() {
		return Pickarea.getRandomPosition();
	}

	public Position Wheat() {
		return Wheat.getRandomPosition();
	}

	public Position Eggs() {
		return Eggs.getRandomPosition();
	}

	public Position SellerS() {
		return SellerS.getRandomPosition();
	}

	public Position Seller() {
		return Seller.getRandomPosition();
	}

	public Position Cookroom() {
		return Cookroom.getRandomPosition();
	}

	public Position LBChest() {
		return LBChest.getRandomPosition();
	}

	public Position PortBankSpot() {
		return PortBank.getRandomPosition();
	}

	public Position FaladorWallcuttingspot() {
		return FaladorWallcuttingspot.getRandomPosition();
	}

	public Position PortWillowspot() {
		return PortWillowspot.getRandomPosition();
	}

	public Position RimmingtonYewSpot() {
		return RimmingtonYewSpot.getRandomPosition();
	}

	public Position VarrocGrandextange() {
		return VarrocGrandextange.getRandomPosition();
	}

	public Position LumbgeSheepPit() {
		return LumbgeSheepPit.getRandomPosition();
	}

	public Position LumbgechickenHut() {
		return LumbgechickenHut.getRandomPosition();
	}

	public Position LumbgeSheepareabig() {
		return LumbgeSheepareabig.getRandomPosition();
	}

	public Position LumbgeCastleWhoolSpinning() {
		return LumbgeCastleWhoolSpinning.getRandomPosition();
	}

}
